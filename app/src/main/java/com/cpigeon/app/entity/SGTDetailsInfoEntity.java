package com.cpigeon.app.entity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/23.
 */

public class SGTDetailsInfoEntity {

    /**
     * cskh : 00003
     * sjhm :
     * diqu : 成都
     * scsj : 2018/1/23 17:14:01
     * ring : FFA76005CCFC3F02
     * id : 547575
     * color : 雨点
     * rpsj : 2018/1/23 16:48:52
     * xingming : 水乡农庄
     * sex :
     * foot : 2018-22-2589631
     * eye :
     * ttzb :
     */

    private String cskh;
    private String sjhm;
    private String diqu;
    private String scsj;
    private String ring;
    private int id;
    private String color;
    private String rpsj;
    private String xingming;
    private String sex;
    private String foot;
    private String eye;
    private String ttzb;
    private List<RPImages> datalist;

    public class RPImages{
        long sj;
        String imgurl;
        String slrurl;
        String tag;
        String updatefootinfo;
        String yearmonth;
        String day;

        public String getYearmonth() {
            return yearmonth;
        }

        public void setYearmonth(String yearmonth) {
            this.yearmonth = yearmonth;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public long getSj() {
            return sj;
        }

        public void setSj(long sj) {
            this.sj = sj;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getSlrurl() {
            return slrurl;
        }

        public void setSlrurl(String slrurl) {
            this.slrurl = slrurl;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
        public String getUpdatefootinfo() {
            return updatefootinfo;
        }

        public void setUpdatefootinfo(String updatefootinfo) {
            this.updatefootinfo = updatefootinfo;
        }
    }



    public List<RPImages> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<RPImages> datalist) {
        this.datalist = datalist;
    }

    public String getCskh() {
        return cskh;
    }

    public void setCskh(String cskh) {
        this.cskh = cskh;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRpsj() {
        return rpsj;
    }

    public void setRpsj(String rpsj) {
        this.rpsj = rpsj;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getTtzb() {
        return ttzb;
    }

    public void setTtzb(String ttzb) {
        this.ttzb = ttzb;
    }
}
