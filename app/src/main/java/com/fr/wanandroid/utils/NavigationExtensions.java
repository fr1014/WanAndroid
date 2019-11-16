package com.fr.wanandroid.utils;

import android.content.Intent;
import android.util.SparseArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.fr.wanandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 * 管理[BottomNavigationView]所需的various graphs
 * <p>
 * 创建时间：2019/10/25
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public final class NavigationExtensions {

    private static final String TAG = "NavigationExtensions";

    public static LiveData<NavController> setupWithNavController(BottomNavigationView navigationView, List<Integer> navGraphIds, FragmentManager fm, int containerId, Intent intent) {

        MutableLiveData<NavController> selectedNavController = new MutableLiveData<>();
        Tag.firstFragmentGraphId = 0;

        //首先为每个NavGraph ID创建一个NavHostFragment
        for (int index = 0; index < navGraphIds.size(); index++) {
            int navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(fm, fragmentTag, navGraphId, containerId);
            //获取其ID
            int graphId = navHostFragment.getNavController().getGraph().getId();

            if (index == 0) {
                Tag.firstFragmentGraphId = graphId;
            }
            // Save to the map
            Tag.graphIdToTagMap.put(graphId, fragmentTag);

            //附加或分离导航主机片段，具体取决于它是否为选定项
            if (navigationView.getSelectedItemId() == graphId) {
                //使用选定的图形更新LiveData
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fm, navHostFragment, index == 0);
            } else {
                detachNavHostFragment(fm, navHostFragment);
            }
        }

        Tag.selectedItemTag = Tag.graphIdToTagMap.get(navigationView.getSelectedItemId());
        Tag.firstFragmentTag = Tag.graphIdToTagMap.get(Tag.firstFragmentGraphId);
        Tag.isOnFirstFragment = Tag.selectedItemTag.equals(Tag.firstFragmentTag);

        //选择导航项目时
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (fm.isStateSaved()) {
                    return false;
                } else {
                    String newlySelectedItemTag = Tag.graphIdToTagMap.get(menuItem.getItemId());
                    if (!Tag.selectedItemTag.equals(newlySelectedItemTag)) {
                        //弹出第一个片段上方的所有内容（固定开始目标)
                        fm.popBackStack(Tag.firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        NavHostFragment selectedFragment = (NavHostFragment) fm.findFragmentByTag(newlySelectedItemTag);
                        //排除第一个fragment标签，因为它始终位于后堆栈中
                        if (!Tag.firstFragmentTag.equals(newlySelectedItemTag) && selectedFragment != null) {
                            FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(
                                    R.anim.nav_default_enter_anim,
                                    R.anim.nav_default_exit_anim,
                                    R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim)
                                    .attach(selectedFragment)
                                    .setPrimaryNavigationFragment(selectedFragment);
                            for (int i = 0; i < Tag.graphIdToTagMap.size(); i++) {
                                String fragmentTag = Tag.graphIdToTagMap.get(Tag.graphIdToTagMap.keyAt(i));
                                if (!fragmentTag.equals(newlySelectedItemTag)) {
                                    Fragment fragment = fm.findFragmentByTag(Tag.firstFragmentTag);
                                    if (fragment != null) {
                                        transaction.detach(fragment);
                                    }
                                }
                            }
                            transaction
                                    .addToBackStack(Tag.firstFragmentTag)
                                    .setReorderingAllowed(true)
                                    .commit();
                        }
                        Tag.selectedItemTag = newlySelectedItemTag;
                        Tag.isOnFirstFragment = Tag.selectedItemTag.equals(Tag.firstFragmentTag);
                        if (selectedFragment != null) {
                            selectedNavController.setValue(selectedFragment.getNavController());
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });

        // Optional: on item reselected, pop back stack to the destination of the graph
        setupItemReselected(navigationView, Tag.graphIdToTagMap, fm);

        // Handle deep link
        setupDeepLinks(navigationView, navGraphIds, fm, containerId, intent);

        // Finally, ensure that we update our BottomNavigationView when the back stack changes
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (!Tag.isOnFirstFragment) {
                    if (!NavigationExtensions.isOnBackStack(fm, Tag.firstFragmentTag)) {
                        navigationView.setSelectedItemId(Tag.firstFragmentGraphId);
                    }
                }

                // Reset the graph if the currentDestination is not valid (happens when the back
                // stack is popped after using the back button).
                NavController controller = selectedNavController.getValue();
                if (controller != null) {
                    if (controller.getCurrentDestination() == null) {
                        controller.navigate(controller.getGraph().getId());
                    }
                }
            }
        });

        return selectedNavController;
    }

    private static class Tag {
        static final SparseArray<String> graphIdToTagMap = new SparseArray<>();
        static int firstFragmentGraphId;
        static String selectedItemTag;
        static String firstFragmentTag;
        static Boolean isOnFirstFragment;
    }

    private static void setupDeepLinks(BottomNavigationView navigationView, List<Integer> navGraphIds, FragmentManager fm, int containerId, Intent intent) {
        for (int index = 0; index < navGraphIds.size(); index++) {
            int navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(index);

            NavHostFragment navHostFragment = obtainNavHostFragment(fm, fragmentTag, navGraphId, containerId);

            //处理Intent
            if (navHostFragment.getNavController().handleDeepLink(intent)) {
                int selectedItemId = navigationView.getSelectedItemId();
                NavGraph graph = navHostFragment.getNavController().getGraph();
                if (selectedItemId != graph.getId()) {
                    navigationView.setSelectedItemId(graph.getId());
                }
            }
        }
    }

    private static void setupItemReselected(BottomNavigationView navigationView, SparseArray<String> graphIdToTagMap, FragmentManager fm) {
        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                String newlySelectedItemTag = graphIdToTagMap.get(menuItem.getItemId());
                NavHostFragment selectedFragment = (NavHostFragment) fm.findFragmentByTag(newlySelectedItemTag);
                if (selectedFragment != null) {
                    NavController navController = selectedFragment.getNavController();
                    // Pop the back stack to the start destination of the current navController graph
                    navController.popBackStack(navController.getGraph().getStartDestination(), false);
                }
            }
        });
    }

    private static void detachNavHostFragment(FragmentManager fm, NavHostFragment navHostFragment) {
        fm.beginTransaction().detach(navHostFragment).commitNow();
    }

    private static void attachNavHostFragment(FragmentManager fm, NavHostFragment navHostFragment, boolean isPrimaryNavFragment) {
        FragmentTransaction transaction = fm.beginTransaction().attach(navHostFragment);
        if (isPrimaryNavFragment) {
            transaction.setPrimaryNavigationFragment(navHostFragment);
        }
        transaction.commitNow();
    }

    private static NavHostFragment obtainNavHostFragment(FragmentManager fragmentManager, String fragmentTag, int navGraphId, int containerId) {
        NavHostFragment existingFragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        //如果导航主机片段存在，则将其返回
        if (existingFragment != null) {
            return existingFragment;
        } else {
            //否则，创建它并返回它
            NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);
            fragmentManager.beginTransaction().add(containerId, navHostFragment, fragmentTag).commitNow();
            return navHostFragment;
        }
    }

    private static Boolean isOnBackStack(FragmentManager fm, String backStackName) {
        int backStackEntryCount = fm.getBackStackEntryCount();
        for (int index = 0; index < backStackEntryCount; index++) {
            String fName = fm.getBackStackEntryAt(index).getName();
            if (fName != null && fName.equals(backStackName)) {
                return true;
            }
        }
        return false;
    }

    private static String getFragmentTag(int index) {
        return "bottomNavigation#" + index;
    }
}
