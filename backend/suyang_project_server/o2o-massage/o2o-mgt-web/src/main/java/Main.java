import org.apache.shiro.util.AntPathMatcher;

public class Main {
    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean result=antPathMatcher.match("sys/res/showEdit/{id}","sys/res/showEdit/123456");
        System.out.println(result);
    }
}
