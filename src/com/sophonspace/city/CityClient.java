package com.sophonspace.city;

import java.io.*;

public class CityClient {

    /**
     * 生成县
     * @param args
     */
    public static void main(String[] args) throws Exception {
        File file = new File("src/com/sophonspace/city/cites.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String area = "";
        StringBuffer buffer = new StringBuffer();
        while ((area = reader.readLine()) != null) {
            if(area.length() == 0){
                continue;
            }
            if(start(area)){
               continue;
            }
            buffer.append("\""+name(area)+"\",");
        }
//        System.out.println(buffer.toString());
        writeData("src/com/sophonspace/city/cites.txt", buffer);
        reader.close();
        fileReader.close();
    }

    public static boolean start(String area){
        return area.trim().endsWith("市辖区") || area.trim().endsWith("名称");
    }


    public static void writeData(String path,StringBuffer buffer) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fileWriter);
        out.write(buffer.toString());
        out.flush();
        out.close();
        fileWriter.close();
    }



    public static String name(String area){
        return area.substring(12, area.length()).trim();
    }
}
