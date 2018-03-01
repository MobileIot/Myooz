package team11.mobileiot.myooz.views;


import java.util.ArrayList;

public class DataSource{
    public static ArrayList<String> data =new ArrayList<>();
    public ArrayList<String> DataSource(int beaconid) {
        if(beaconid==1) {
            data.add("https://pi.tedcdn.com/r/tedideas.files.wordpress.com/2017/05/featured_art_heal_forests.jpg");
            data.add("https://i.pinimg.com/736x/c8/45/d8/c845d8ddcbccf0c874eff927b4d754fe--vintage-nature-photography-travel-photography.jpg");
            data.add("https://images.metmuseum.org/CRDImages/cl/web-large/DP102839.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DP135156.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ci/web-large/DT436.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ao/web-large/DT1276.jpg");
            data.add("https://images.metmuseum.org/CRDImages/aa/web-large/37.131.4_002Sept2014.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ep/web-large/DP287624.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg");
        }else{
            data.add("https://images.metmuseum.org/CRDImages/cl/web-large/DP102839.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DP135156.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ci/web-large/DT436.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ao/web-large/DT1276.jpg");
            data.add("https://images.metmuseum.org/CRDImages/aa/web-large/37.131.4_002Sept2014.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ep/web-large/DP287624.jpg");
            data.add("https://images.metmuseum.org/CRDImages/ma/web-large/DT1432.jpg");
        }
        return data;
    }
}
