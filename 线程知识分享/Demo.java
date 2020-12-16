import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.locks.LockSupport;

class Name {// 用于演示继承
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class MyThread extends Thread {// 继承Thread类，作为线程的实现类
    private String name;// 表示线程的名称

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {// 覆写run()方法
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "运行，i = " + i);
        }
    }
};

class MyThreadImpl extends Name implements Runnable {// 自行实现Runnable并继承其它类
    private Integer count = 0;
    private final String id = Double.toString(Math.random());
    private String type = "";
    private Object obj;
    private final String SLEEP = "sleep";
    private final String SHARE = "share";
    private final String VOLATILEA = "volatileA";
    private final String VOLATILEB = "volatileB";
    
    
    public volatile Integer numberV = 1;
    public Integer number = 1;

    public MyThreadImpl(String name) {
        setName(name);
    }

    public MyThreadImpl(String name, String type, Object... params) {
        setName(name);
        this.type = type;
        if (params.length > 0) {
            this.obj = params[0];
        }
    }

    public void run() {
        switch (type) {
            case SLEEP:
                while (count < 10) {
                    if (count == 5) {
                        try {
                            System.out.println("======线程Sleep======");
                            Thread.sleep(1000);
                        } catch (InterruptedException exception) {

                        }
                    }
                    System.out.println(getName() + "运行，i = " + count);
                    count++;
                }
                break;
            case SHARE:
                while (count < 10) {
                    if (null != obj) {
                        try {
                            Method methodGet = obj.getClass().getMethod("get", Object.class);
                            Method methodPut = obj.getClass().getMethod("put", Object.class, Object.class);
                            if (null != methodGet && null != methodPut) {
                                String value = Double.toString(Math.random());
                                methodPut.invoke(obj, Integer.toString(count), value);
                                System.out
                                        .println(String.format("%1$s 运行，操作map的id为%2$s，置入key为%3$s,置入的值为%4$s,获取到的值为%5$s",
                                                getName(), methodGet.invoke(obj, "id"), Integer.toString(count), value,
                                                methodGet.invoke(obj, Integer.toString(count))));
                            }
                        } catch (Exception exception) {
                        }
                    }
                    count++;
                }
            case VOLATILEA:
                while (numberV < 20) {
                    numberV++;
                    System.out.println(getName() + "运行，i = " + numberV);
                }
                break;
            case VOLATILEB:
                System.out.println(getName() + "运行，i = " + obj);
                break;
            default:
                while (count < 10) {
                    System.out.println(getName() + "运行，i = " + count);
                    count++;
                }
                break;
        }
    }
}

public class Demo {

    public static void main(String args[]) throws InterruptedException {
        // 线程实现方式代码段
        // MyThread mt1 = new MyThread("线程A ");
        // MyThread mt2 = new MyThread("线程B ");
        // mt1.start();
        // mt2.start();
        // new Thread(new MyThreadImpl("线程C ")).start();

        // sleep代码段
        // new Thread(new MyThreadImpl("线程A ", "sleep")).start();
        // for (var i = 0; i < 10; i++) {
        // System.out.println("MainThread运行，i = " + i);
        // }

        // join代码段
        // MyThread mt1 = new MyThread("线程A ");
        // mt1.start();
        // for (var i = 0; i < 10; i++) {
        // if (i == 5) {
        // mt1.join();
        // }
        // System.out.println("MainThread运行，i = " + i);
        // }

        // 线程资源共享代码段1
        // MyThreadImpl mt3 = new MyThreadImpl("线程A","share");
        // new Thread(mt3).start();
        // new Thread(mt3).start();

        // 线程资源共享代码段2
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", Double.toString(Math.random()));
        MyThreadImpl mt4 = new MyThreadImpl("线程A", "share", map);
        MyThreadImpl mt5 = new MyThreadImpl("线程B", "share", map);
        Thread t1 =new Thread(mt4);
        t1.setPriority(1);
        Thread t2 =new Thread(mt5);
        t2.setPriority(2);
        t1.start();
        t2.start();
    }
};
