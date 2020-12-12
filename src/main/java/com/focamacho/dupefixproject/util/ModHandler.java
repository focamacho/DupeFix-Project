package com.focamacho.dupefixproject.util;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModHandler {

    private static final HashMap<String, File> cachedMods = new HashMap<>();

    static{

        File folder = new File("mods");
        scan(folder);

    }

    private static void scan(File folder){

        if(folder.exists() && folder.isDirectory() && folder.listFiles() != null){

            File[] mods = folder.listFiles(f -> f.getName().endsWith(".jar") || f.isDirectory());

            if(mods != null) {
                for (File mod : mods) {

                    if(mod.isDirectory()){
                        scan(mod);
                    }

                    try {

                        String modid = getModID(mod);

                        if(modid != null){
                            cachedMods.put(modid, mod);
                        }

                    } catch (IOException ignored) {}
                }
            }
        }
    }

    private static String getModID(File file) throws IOException {

        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> zipEntryEnumeration = zipFile.entries();

        while (zipEntryEnumeration.hasMoreElements()){

           ZipEntry zipEntry = zipEntryEnumeration.nextElement();

           if(zipEntry != null && zipEntry.getName().equalsIgnoreCase("mcmod.info")){

               InputStream inputStream = zipFile.getInputStream(zipEntry);

               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

               String read;

               while ((read = bufferedReader.readLine()) != null){
                   if(read.contains("\"modid\"")){
                       return read
                               .replace("\"",       "")
                               .replace("modid",    "")
                               .replace(":",        "")
                               .replace(",",        "")
                               .replace("\t",       "")
                               .replace("\n",       "")
                               .replace(" ",        "")
                               .replace("{",        "")
                               .replace("}",        "");
                   }
               }
           }
        }

        return null;
    }

    public static boolean isPresent(String modid){
        return cachedMods.containsKey(modid);
    }

    public static boolean load(String modid){

        if(!isPresent(modid)){
            return false;
        }

        try{
            loadJar(cachedMods.get(modid));
        } catch (MalformedURLException ignored) {
            return false;
        }

        return true;

    }

    private static void loadJar(File jar) throws MalformedURLException {
        ((LaunchClassLoader) ModHandler.class.getClassLoader()).addURL(jar.toURI().toURL());
        CoreModManager.getReparseableCoremods().add(jar.getName());
    }

    public static void clear(){
        cachedMods.clear();
    }

}
