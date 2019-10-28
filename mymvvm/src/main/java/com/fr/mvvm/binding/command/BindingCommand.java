package com.fr.mvvm.binding.command;

/**
 * 创建时间：2019/10/19
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class BindingCommand<T> {
    private BindingAction execute;
    private BindingConsumer<T> consumer;

    public BindingCommand(BindingAction execute){
        this.execute = execute;
    }

    /**
     * 带泛型参数的命令绑定
     */
    public BindingCommand(BindingConsumer<T> execute){
        this.consumer = execute;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute(){
       if (execute!=null){
           execute.call();
       }
    }

    /**
     * 执行带泛型的命令
     * @param parameter 泛型参数
     */
    public void execute(T parameter){
        if (consumer!=null){
            consumer.call(parameter);
        }
    }

}
