package team11.mobileiot.myooz.views;

/**
 * Created by flora on 2/28/18.
 */

public class Comment {
    public String url;
    public String title;
    public String subtitle;
    public String content;
    private String time;
    private Integer numkudos;
    Comment(String url,String title,String content,String time,Integer numkudos){
        this.url=url;
        this.title=title;
        this.content=content;
        this.time=time;
        this.numkudos=numkudos;
        this.subtitle=time+ "  kudus: "+numkudos;
    }
    public void updateSubtitle(Integer numkudos){
        this.numkudos=numkudos;
        this.subtitle=time+ "  kudus: "+numkudos;
    }
}

