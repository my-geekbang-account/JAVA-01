import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("HelloClassLoader <XLASS>");
      System.exit(1);
    }
    String path = args[0];

    try {

      HelloClassLoader helloClassLoader = new HelloClassLoader();
      Class clazz = helloClassLoader.findClass(path);
      Object instance = clazz.newInstance();
      Method hello = clazz.getMethod("hello");
      hello.invoke(instance);

    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    String filename = Paths.get(name).getFileName().toString();
    if (filename.indexOf(".") > 0) {
      filename = filename.substring(0, filename.lastIndexOf("."));
    }
    try {
      byte[] bytes = Files.readAllBytes(Paths.get(name));
      for (int i = 0; i < bytes.length; i++) {
        bytes[i] = (byte) (255 - (int) bytes[i]);
      }
      return defineClass(filename, bytes, 0, bytes.length);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ClassNotFoundException(filename, e);
    }
  }

}
