package ru.notificator.sirius.siriusnotificator;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ServerSN {
    private static final String ip = "95.163.180.180";
    private static final String path = "/api/v1/";

    public static String addLink(String childMac, String curMac) throws Exception {
        URL url = new URL(ip + path + "addLink");
        HttpURLConnection hu = (HttpURLConnection) url.openConnection();
        hu.setRequestMethod("POST");
        hu.setDoOutput(true);
        hu.connect();
        PrintWriter out = new PrintWriter(hu.getOutputStream());
        out.print(childMac + " " + curMac);
        out.close();
        Scanner in = new Scanner(hu.getInputStream());
        while (!in.hasNext()) {
            Thread.sleep(100);
        }
        String res = "";
        while (in.hasNext()) {
            res += in.nextLine() + "\n";
        }
        return res;
    }

    public static String removeLink(String childMac, String curMac) throws Exception {
        URL url = new URL(ip + path + "removeLink");
        HttpURLConnection hu = (HttpURLConnection) url.openConnection();
        hu.setRequestMethod("POST");
        hu.setDoOutput(true);
        hu.connect();
        PrintWriter out = new PrintWriter(hu.getOutputStream());
        out.print(childMac + " " + curMac);
        out.close();
        Scanner in = new Scanner(hu.getInputStream());
        while (!in.hasNext()) {
            Thread.sleep(100);
        }
        String res = "";
        while (in.hasNext()) {
            res += in.nextLine() + "\n";
        }
        return res;
    }

    public static String removeChild(String childMac) throws Exception {
        URL url = new URL(ip + path + "removeChild");
        HttpURLConnection hu = (HttpURLConnection) url.openConnection();
        hu.setRequestMethod("POST");
        hu.setDoOutput(true);
        hu.connect();
        PrintWriter out = new PrintWriter(hu.getOutputStream());
        out.print(childMac);
        out.close();
        Scanner in = new Scanner(hu.getInputStream());
        while (!in.hasNext()) {
            Thread.sleep(100);
        }
        String res = "";
        while (in.hasNext()) {
            res += in.nextLine() + "\n";
        }
        return res;
    }

    public static String putInfo(String childMac, String token, String name, String surname) throws Exception {
        URL url = new URL(ip + path + "putInfo");
        HttpURLConnection hu = (HttpURLConnection) url.openConnection();
        hu.setRequestMethod("POST");
        hu.setDoOutput(true);
        hu.connect();
        PrintWriter out = new PrintWriter(hu.getOutputStream());
        out.print(childMac + " " + token + " " + name + " " + surname);
        out.close();
        Scanner in = new Scanner(hu.getInputStream());
        while (!in.hasNext()) {
            Thread.sleep(100);
        }
        String res = "";
        while (in.hasNext()) {
            res += in.nextLine() + "\n";
        }
        return res;
    }

    public static String getInfo(String childMac) throws Exception {
        URL url = new URL(ip + path + "getInfo");
        HttpURLConnection hu = (HttpURLConnection) url.openConnection();
        hu.setRequestMethod("POST");
        hu.setDoOutput(true);
        hu.connect();
        PrintWriter out = new PrintWriter(hu.getOutputStream());
        out.print(childMac);
        out.close();
        Scanner in = new Scanner(hu.getInputStream());
        while (!in.hasNext()) {
            Thread.sleep(100);
        }
        String res = "";
        while (in.hasNext()) {
            res += in.nextLine() + "\n";
        }
        return res;
    }
}
