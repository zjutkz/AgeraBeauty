package com.zjutkz.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class Beauty implements Serializable{


    /**
     * count : 10
     * error : false
     * results : [{"desc":"10.27","ganhuo_id":"56cc6d1d421aa95caa70755f","publishedAt":"2015-10-27T02:43:16.906000","readability":"","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bjw1exfffnlf2gj20hq0qoju9.jpg","who":"张涵宇"},{"desc":"5.20。\n520爱你，就给你甜甜的笑。今日特推！~~（づ￣3￣）づ╭❤～","ganhuo_id":"56cc6d1d421aa95caa7075c5","publishedAt":"2015-05-21T10:05:06.527000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1esahpyv86sj20hs0qomzo.jpg","who":"张涵宇"},{"desc":"6.10\u2014\u2014（2）","ganhuo_id":"56cc6d1d421aa95caa7076df","publishedAt":"2015-06-10T04:12:03.656000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1esxxi1vbq0j20qo0hstcu.jpg","who":"张涵宇"},{"desc":"6.10\u2014\u2014（1）","ganhuo_id":"56cc6d1d421aa95caa7076e1","publishedAt":"2015-06-10T04:12:04.272000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1esxxiw20rej20qo0hstcp.jpg","who":"张涵宇"},{"desc":"6.18","ganhuo_id":"56cc6d1d421aa95caa7076f8","publishedAt":"2015-06-18T03:50:59.419000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1et80fw2p80j20qo0hsdj1.jpg","who":"张涵宇"},{"desc":"7.17","ganhuo_id":"56cc6d1d421aa95caa707701","publishedAt":"2015-07-17T03:43:22.394000","readability":"","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1eu5jpf3lamj20f00mitck.jpg","who":"张涵宇"},{"desc":"5.19\n","ganhuo_id":"56cc6d1d421aa95caa707751","publishedAt":"2015-05-20T03:57:51.477000","readability":"","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1es8c7ucr0rj20hs0qowhl.jpg","who":"张涵宇"},{"desc":"7.3","ganhuo_id":"56cc6d1d421aa95caa70774e","publishedAt":"2015-07-03T04:12:02.419000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1etpfol394kj20qo0hsdiw.jpg","who":"张涵宇"},{"desc":"8.18\u2014\u2014（1）","ganhuo_id":"56cc6d1d421aa95caa70779f","publishedAt":"2015-08-18T03:58:47.771000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1ev6jgvbt8ij20qo0hrdil.jpg","who":"张涵宇"},{"desc":"8.18\u2014\u2014（2）","ganhuo_id":"56cc6d1d421aa95caa7077a0","publishedAt":"2015-08-19T03:56:32.545000","readability":"","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1ev6jh1hbsgj20hr0qoq5s.jpg","who":"张涵宇"}]
     */

    public int count;
    public boolean error;
    /**
     * desc : 10.27
     * ganhuo_id : 56cc6d1d421aa95caa70755f
     * publishedAt : 2015-10-27T02:43:16.906000
     * readability : 
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/7a8aed7bjw1exfffnlf2gj20hq0qoju9.jpg
     * who : 张涵宇
     */

    public List<BeautyEntity> results;


    public static class BeautyEntity {
        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
