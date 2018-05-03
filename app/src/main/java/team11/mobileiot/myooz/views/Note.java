package team11.mobileiot.myooz.views;

/**
 * Created by flora on 2/28/18.
 */

public class Note {
    public String url;
    public String content;
    public String time;
    public Integer numkudos;
    Note(String url, String title, String content, String time, Integer numkudos){
        this.url=url;
        this.content=content;
        this.time=time;
        this.numkudos=numkudos;
    }
    public void updateSubtitle(Integer numkudos){
        this.numkudos=numkudos;
    }
}

