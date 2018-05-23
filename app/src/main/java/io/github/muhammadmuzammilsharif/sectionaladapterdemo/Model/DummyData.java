package io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model;

import org.jetbrains.annotations.NotNull;

import io.github.muhammadmuzammilsharif.interfaces.SectionalUniqueObject;

/*
 * Created by M_Muzammil Sharif on 21-May-18.
 */
public class DummyData implements SectionalUniqueObject<String> {
    private String name, img, date;
    private int id;

    public DummyData(int id, String img, String name, String date) {
        this.name = name;
        this.img = img;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getSection() {
        return getDate();
    }

    @NotNull
    @Override
    public Object getUniqueKey() {
        return getId();
    }
}
