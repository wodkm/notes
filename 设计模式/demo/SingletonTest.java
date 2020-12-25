class Singleton{
    private static volatile Singleton singleton;

    private Singleton(){
        
    }

    public static synchronized Singleton getSingleton(){
        if(null==singleton){
            singleton = new Singleton();
        }
        return singleton;
    }
}

public class SingletonTest {
    public void main(){
        Singleton singleton = Singleton.getSingleton();
    }
}
