package com.sophonspace.city;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // 读取数据
        File file = new File("src/areas.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String area = "";
        String provinceSuffix = "";
        String province = "";
        String citySuffix = "";
        String city = "";
        StringBuffer buffer = new StringBuffer("{");
        while ((area = reader.readLine()) != null) {
            String areaSuffix = getCode(area);
            // 解析数据
            if(isProvince(areaSuffix)){
                boolean provinceEnd = false;
                provinceSuffix = provinceSuffix(area);
                String tempName = name(area);
                if(!province.equals(tempName)){
                    province = tempName;
                    provinceEnd = true;
                }

                if(provinceEnd){
                    buffer.append("]},\r\n");
                }
                buffer.append("\""+province+"\": {");
                continue;
            }
            if(isCity(provinceSuffix, areaSuffix)){
                boolean cityEnd = false;
                citySuffix = citySuffix(area);
                String tempName = name(area);
                if(!city.equals(tempName)){
                    city = tempName;
                    cityEnd = true;
                }
                if(cityEnd){
                    buffer.append("],\r\n");
                }

                buffer.append("\""+city+"\": [");
                continue;
            }
            if(isArea(citySuffix, areaSuffix)){
                String areaName = name(area);
                buffer.append("\""+areaName+"\",");
                buffer.append("\r\n");
            }


            // 保存数据
        }
        buffer.append("}");
//        System.out.println(buffer.toString());
        writeData("areas.json",buffer);
        reader.close();
        fileReader.close();

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

    /**
     *读取文件,每次读取一行
     */
    public static void readDatas(String path) throws Exception {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String area = "";
        while ((area = reader.readLine()) != null) {
            System.out.println(area);
        }
        reader.close();
        fileReader.close();
    }

    public static String getCode(String area){
        return area.substring(0, 6);
    }

    /**
     * 判读是否是省
     */
    public static boolean isProvince(String area) {
        return area.endsWith("0000");
    }

    /**
     * 获取省的编号
     */
    public static String provinceSuffix(String area){
        return area.substring(0,2);
    }

    /**
     * 判断是否是市
     */
    public static boolean isCity(String provinceSuffix, String area) {
        return area.startsWith(provinceSuffix) && area.endsWith("00");
    }

    /**
     * 获取市的编号
     */
    public static String citySuffix(String area){
        return area.substring(0,4);
    }

    /**
     * 判断地区是否是该市下面的县
     */
    public static boolean isArea(String citySuffix, String area) {
        return area.startsWith(citySuffix);
    }

    /**
     * 获取名字
     */
    public static String name(String area) {
        return area.substring(6,area.length()).trim();
    }
}
