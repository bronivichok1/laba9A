package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.servlet.ServletContext;
import com.company.AdList;
public abstract class AdListHelper {
    // Логический путь к файлу, в котором хранятся данные об объявлениях
    private static final String ADS_FILENAME = "WEB-INF/ads.dat";
    // Полный путь к файлу, в котором хранятся данные об объявлениях
    private static String ADS_PATH = null;
    // Читает данные объявлениях из файла хранилища и формирует на их основеобъект AdList.
    public static AdList readAdList(ServletContext context) {
        try {
            // Определяем физический путь к файлу
            ADS_PATH = context.getRealPath(ADS_FILENAME);
            // Создаем объектный поток ввода на основе файлового потока
            ObjectInputStream in = new ObjectInputStream(new
                    FileInputStream(ADS_PATH));
            return (AdList)in.readObject();
        } catch (Exception e) {
// Если возникли проблемы с чтением из файла, возвращаемпустой список
            return new AdList();
        }
    }
    // Сохраняет в файле хранилища содержимое списка объявлений
    public static void saveAdList(AdList ads) {
// Путь к файлу с данными уже находится в переменной ADS_PATH
// Она была инициализирована при загрузке данных в процессеинициализации приложения
        synchronized (ads) {
            try {
// Создаем объектный поток вывода на основе файловогопотока
                ObjectOutputStream out = new ObjectOutputStream(new
                        FileOutputStream(ADS_PATH));
// Записываем содержимое объекта в поток
                out.writeObject(ads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

