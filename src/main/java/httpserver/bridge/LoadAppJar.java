package httpserver.bridge;


import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadAppJar {

    private static final String FILE_SPERATE = "/";

    private static final String CLASS_SUFFIX = ".class";

    private static final Map<String, Map<String,byte[]>> clazzMap = new ConcurrentHashMap<String, Map<String,byte[]>>(16);

    private static final List<JarFile> jarFiles = new ArrayList<>(10);

    public static void loadJars(String jarFilePath) throws IOException {
        File file = new File(jarFilePath);
        if (file == null) {
            return;
        } else if (file.isFile() && file.getName().endsWith(".jar")) {
            jarFiles.add(new JarFile(file));
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                loadJars(f.getAbsolutePath());
            }
        }
    }

    public static void readJar(JarFile jarFile) {
        Map<String,byte[]> jarClazzMap = new HashMap<>(jarFile.size()+8);
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            String className = entry.getName();
            if (className.endsWith(CLASS_SUFFIX)) {
                String clazz = className.replace(CLASS_SUFFIX, "").replaceAll("/", ".");
                try {
                    InputStream inputStream = jarFile.getInputStream(entry);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[inputStream.available()];
                    int read = 0;
                    while ((read = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, read);
                    }
                    jarClazzMap.put(clazz,outputStream.toByteArray());
                } catch (IOException e) {

                }
            }
        }
        clazzMap.put(jarFile.getName(),jarClazzMap);
    }

    public static List<JarFile> getJarFiles() {
        return jarFiles;
    }

    public static Map<String, Map<String, byte[]>> getClazzMap() {
        return clazzMap;
    }
}