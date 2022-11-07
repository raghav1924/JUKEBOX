import javax.sound.sampled.*;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.File;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
import java.io.Console;
import java.util.concurrent.locks.ReadWriteLock;

public class Main {
    static Scanner t=new Scanner(System.in);

    public Statement getConnectionStatment(){
        Connection con =null;
        Statement st =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            st=con.createStatement();
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("Exception in getConnectionStatement");
        }
        catch (ClassNotFoundException ex){
            System.out.println(ex);
            System.out.println("Exception in getConnectionStatement");
        }
        return st;
    }

// *******************   Display ALL SONGS
    public static int displayAllSong(Statement st,String order){
        int count=0;
        try {
            System.out.println("");
            System.out.printf(" %-10s\t%-30s\t%-6s  %6s  \n", "Song", "Artist", "Duration", "SongId");
            ResultSet rs1 = st.executeQuery("select * from musictable order by " + order);
            while (rs1.next()) {
                count++;
                System.out.printf(" %-10s\t%-30s\t%-6s\t  %4s  \n", rs1.getString(4), rs1.getString(2), rs1.getString(5), rs1.getInt(1));
            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("Display ALL SONGS");
        }
        return count;
    }
        // *******************   DISPLAY ALL PODCAST
    public static int displayAllPodcast(Statement st,String order){
        int count=0;
        try {
            System.out.println("");
            System.out.printf(" %-30s\t%-10s\t\t\t%-6s\t%-6s  \n", "Podcast", "Host", "EpisodeId", "Episode No");
            ResultSet rs1 = st.executeQuery("select * from podcasttable order by " + order);
            while (rs1.next()) {
                count++;
                System.out.printf(" %-30s\t%-20s\t%-6s\t%6s  \n", rs1.getString(3), rs1.getString(2), rs1.getInt(5), rs1.getInt(6));
            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("DISPLAY ALL PODCAST");
        }
        return count;

    }
    // ********  SEARCH IN SONG LIKE SONG NAME , ALBUM NAME , ARTIST NAME
    public static int searchInSong(int t,Statement st,String str){
        ResultSet rs1=null;
        int count=0;
        try {

            rs1 = st.executeQuery("select * from musictable");
            System.out.printf(" %-10s\t%-30s\t%-6s  %6s  \n", "Song", "Artist", "Duration", "SongId");
            while (rs1.next()) {
                if (rs1.getString(t).toLowerCase().contains(str.toLowerCase())) {
                    count++;
                    System.out.printf(" %-10s\t%-30s\t%-6s\t%4s  \n",rs1.getString(4),rs1.getString(2),rs1.getString(5),rs1.getInt(1));
                }
            }
            if (count==0){
                System.out.println("No Result");
            }
        }
        catch (SQLException e){
            e.getMessage();
            System.out.println(e);
        }
        return count;
    }
    // ******************N  SEARCH IN PODCAST NAME OR HOST NAME

    public static int searchInPodcast(int t,Statement st,String str){//
        ResultSet rs1=null;
        int count=0;
        try {

            rs1 = st.executeQuery("select * from podcasttable");
            System.out.printf(" %-30s\t%-20s\t%-6s\t\t%-6s\t\t%-50s  \n","Podcast","Host","EpisodeId","Episode No","Episode Name");
            while (rs1.next()) {
                if (rs1.getString(t).toLowerCase().contains(str.toLowerCase())) {
                    count++;
                    System.out.printf(" %-30s\t%-25s\t%-6s\t%6s\t\t\t\t%-50s  \n",rs1.getString(3),rs1.getString(2),rs1.getInt(5),rs1.getInt(6),rs1.getString(11));
                }
            }
            if (count==0){
                System.out.println("No Result");
            }
        }
        catch (SQLException e){
            e.getMessage();
            System.out.println(e);
            System.out.println(" SEARCH IN PODCAST NAME OR HOST NAME");
        }
        return count;
    }

    // ****************   SEARCH FOR SONG *****************
    public static void searchForSong(Statement st){
        System.out.println("");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("   1 => Search with Song Name  |  2 => Search with Album Name  |  3 => Search with Artist Name");
        System.out.println("Enter your choice:");
        byte n3=t.nextByte();
        if (n3==1){
            System.out.println("");
            System.out.println("Enter Song Name");
            String songname=t.next();
            searchInSong(4,st,songname);
        }
        if (n3==2){
            System.out.println("");
            System.out.println("Enter Album Name");
            String album=t.next();
            searchInSong(3,st,album);
        }
        if (n3==3){
            System.out.println("");
            System.out.println("Enter Artist Name");
            String artist=t.next();
            searchInSong(2,st,artist);
        }

    }

    // ****************   SEARCH FOR PODCAST *****************
    public static void searchForSPodcast(Statement st){
        System.out.println("");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("       1 => Search by Podcast Name | 2 => Search by Episode Name | 3 => Search by Host Name");
        System.out.println("Enter your choice: ");
        byte n3=t.nextByte();
        if (n3==1){
            System.out.println("");
            System.out.println("Enter Podcast Name");
            String podcast=t.next();
            searchInPodcast(3,st,podcast);
        }
        if (n3==2){
            System.out.println("");
            System.out.println("Enter Episode Name");
            String episodename=t.next();
            searchInPodcast(11,st,episodename);
        }
        if (n3==3){
            System.out.println("");
            System.out.println("Enter Host Name");
            String host=t.next();
            searchInPodcast(2,st,host);
        }

    }

    // ******************************     USER SEARCH ********************
    public  static void userSearch(String phone,Statement st){
        ResultSet rs=null;
        try {
                rs=st.executeQuery("select * from user where phoneNo='"+phone+"'");
                rs.next();
            System.out.printf("%-4s\t%-10s\t%-6s\t%-10s\t%15s  \n","UserID","UserName","Name","Phone Number","Password");
            System.out.printf("%-4s\t%-10s\t%-6s\t%-10s\t%15s  \n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));

        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("USER SEARCH");
        }

    }
    // **********************   SONG PLAY METHODS ***********
    public static int playSong(int id,String stat){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st = con.createStatement();
            ResultSet rs2=st.executeQuery("select count(songid) from musictable where songid="+id);
            rs2.next();
            System.out.println(rs2.getInt(1));
            //rs2.next();
            if (rs2.getInt(1)==0){
                System.out.println("No Song with this ID");
                return 0;
            }
            ResultSet rs = st.executeQuery("select URL,songname from musictable where songid="+id);
            rs.next();
            System.out.println(rs.getString(1));
            File path = new File(rs.getString(1));
            AudioInputStream au = AudioSystem.getAudioInputStream(path.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(au);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            long timeposition = 0;
            String status = "Playing";


            while (!status.equalsIgnoreCase("stop")) {
                System.out.println("");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("            SONG PLAYINNG NOW: "+rs.getString(2)+"  SONG TIME LENGTH: "+clip.getMicrosecondLength()/1000000+" Seconds");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println();
                System.out.println("    --------------  -----------------------  ---------------   ----------------------");
                System.out.println("    | 1 => Pause |  | 2 => Stop & Go Back |  | t3 => Replay |  | 4 => Forward/Rewind |");
                System.out.println("    --------------  -----------------------  ---------------   ----------------------");
                System.out.println();
                if(stat.equals("playlist")){
                    System.out.println(" ---------------   -----------------------------   ------------------   ----------------------  ");
                    System.out.println(" | 5 => Resume |   | 6 => Other Song/Episode |   | 7 => Next Song |   | 8 => Previous Song |");
                    System.out.println(" ---------------   -----------------------------   ------------------   ----------------------  ");

                }else {
                    System.out.println(" ---------------   ---------------------   ------------------   ----------------------  ");
                    System.out.println(" | 5 => Resume |   | 6 => Another Song |   | 7 => Next Song |   | 8 => Previous Song |");
                    System.out.println(" ---------------   ---------------------   ------------------   ----------------------  ");
                }
                System.out.println("Enter your Choice");
                byte c = t.nextByte();
                switch (c) {
                    case 1:
                        timeposition = clip.getMicrosecondPosition();
                        clip.stop();
                        System.out.println("Time Remaining: "+(clip.getMicrosecondLength()-timeposition)/1000000+"Secs");
                        break;
                    case 2:
                        if(stat.equals("playlist")){
                            clip.stop();
                            status = "stop";
                            return  -12;
                        }else {
                            clip.stop();
                            status = "stop";
                            return 0;
                        }
                    case 3:
                        clip.stop();
                        clip.setMicrosecondPosition(0L);
                        clip.start();
                        break;
                    case 4:
                        System.out.println("Enter Second to Forward or for Rewind Enter the second with (-ve symbol) ");
                        long s = t.nextLong();
                        timeposition = clip.getMicrosecondPosition();
                        clip.setMicrosecondPosition(timeposition + s * 1000000);
                        clip.start();
                        break;
                    case 5:
                        clip.setMicrosecondPosition(timeposition);
                        clip.start();
                        break;
                    case 6:
                        if(stat.equals("playlist")){
                            clip.stop();
                            return  -8;
                        }else {
                            System.out.println("Enter SongId");
                            int nextsong=t.nextInt();
                            clip.stop();
                            //playSong(nextsong);
                            return nextsong;
                        }

                    case 7:
                        clip.stop();
                        return id;
                    case 8:
                        clip.stop();
                        return id-2;
                    default:
                        System.out.println("Invalid Entry");
                }
            }

        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("SONG PLAY METHODS");
        }
        return id;
    }
    // **********************   PODCAST PLAY METHODS ***********

    public static int playPodcast(int id,String stat){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st = con.createStatement();
            ResultSet rs2=st.executeQuery("select count(episodeid) from podcasttable where episodeid="+id);
            rs2.next();
            if (rs2.getInt(1)==0){
                System.out.println("No Episode");
                return 0;
            }
            ResultSet rs = st.executeQuery("select URL,episodename from podcasttable where episodeid="+id);
            rs.next();
            System.out.println(rs.getString(1));
            File path = new File(rs.getString(1));
            AudioInputStream au = AudioSystem.getAudioInputStream(path.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(au);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            long timeposition = 0;
            String status = "Playing";

            while (!status.equalsIgnoreCase("stop")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("         POCAST EPISODE PLAYINNG NOW: "+rs.getString(2));
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                System.out.println();
                System.out.println("               ~~~~~>    EISODE TIME LENGTH: "+clip.getMicrosecondLength()/1000000+" Secondds");
                System.out.println();
                System.out.println("      --------------  -----------------------  ---------------   ----------------------");
                System.out.println("      | 1 => Pause |  | 2 => Stop & Go Back |  | t3 => Replay |  | 4 => Forward/Rewind |");
                System.out.println("      --------------  -----------------------  ---------------   ----------------------");
                System.out.println();
                if(stat.equals("playlist")){
                    System.out.println(" ---------------   -----------------------------   ------------   ------------  ");
                    System.out.println(" | 5 => Resume |   | 6 => Other Song/Episode |   | 7 => Next  |   | 8 => Previous  |");
                    System.out.println(" ---------------   -----------------------------   ------------   ------------  ");

                }else {
                    System.out.println(" ---------------   ------------------------   ---------------------   ----------------------  ");
                    System.out.println(" | 5 => Resume |   | 6 => Another Episode |   | 7 => Next Episode |   | 8 => Previous Episode |");
                    System.out.println(" ---------------   ------------------------   ---------------------   ----------------------  ");
                }
                System.out.println("Enter your Choice");
                byte c = t.nextByte();
                switch (c) {
                    case 1:
                        timeposition = clip.getMicrosecondPosition();
                        clip.stop();
                        System.out.println("Time Remaining: "+(clip.getMicrosecondLength()-timeposition)/1000000+"Secs");
                        break;
                    case 2:
                        if(stat.equals("playlist")){
                            clip.stop();
                            status = "stop";
                            return  -12;
                        }else {
                            clip.stop();
                            status = "stop";
                            return 0;
                        }

                    case 3:
                        clip.stop();
                        clip.setMicrosecondPosition(0L);
                        clip.start();
                        break;
                    case 4:
                        System.out.println("Enter Second to Forward or for Rewind Enter the second with (-ve symbol) ");
                        long s = t.nextLong();
                        timeposition = clip.getMicrosecondPosition();
                        clip.setMicrosecondPosition(timeposition + s * 1000000);
                        clip.start();
                        break;
                    case 5:
                        clip.setMicrosecondPosition(timeposition);
                        clip.start();
                        break;
                    case 6:
                        if(stat.equals("playlist")){
                            clip.stop();
                            return  -8;
                        }else {
                            System.out.println("Enter EpisodeId");
                            clip.stop();
                            //playPodcast(t.nextInt());
                            return t.nextInt();
                        }

                    case 7:
                        clip.stop();
                        return id;
                    case 8:
                        clip.stop();
                        return id-2;
                    default:
                        System.out.println("Invalid Entry");
                }
            }

        }
        catch (Exception e){
            System.out.println(e);
            e.getMessage();
            e.printStackTrace();
            System.out.println("PODCAST PLAY METHODS");
        }
        return id;

    }

    //*******************  USER PLAYLIST   *************************************

    public static void userplaylist(int us,Statement st){
        try {
            ResultSet rs = st.executeQuery("select distinct(playlistname) from userplaylist where userid=" + us);
            int count=0;
            System.out.println("----- Your PlayLists -------");
            while (rs.next()) {
                count++;
                System.out.println(rs.getString(1));
            }
            if (count==0){
                System.out.println("No Playlist created till now");
            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println(" USER PLAYLIST");
        }
    }

    // *******************  playsongthroughplaylist
    public static int playThePlaylist(Statement st,String str,int id,String playid){
        ResultSet rs=null;
        try {
            int count=0;
            Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st2=con.createStatement();
            ResultSet rs2=st2.executeQuery("select count(playlistname) from userplaylist where playlistname='"+str+"'");
            rs2.next();
            rs=st.executeQuery("select songid,podcastid from userPlaylist where playlistname='"+str+"' and userid="+id);
            int count2=rs2.getInt(1);
            List<String> list=new ArrayList<>();

            while (rs.next()) {
                Integer car = rs.getInt(1);//songI
                Integer car2 = rs.getInt(2);//podxasI
                if (car == 0 && car2 == 0) {
                    continue;
                }
                if (car == 0) {
                    list.add("P," + rs.getInt(2));
                }
                if (car2 == 0) {
                    list.add("S," + rs.getInt(1));
                }
            }
            int c=0;
            for (String temp:list) {
                if (temp.equals(playid)){
                    break;
                }
                ++c;

            }
            if (c==list.size()){
                System.out.println("This Song/Poadcast not present");
                return 0;
            }

            int t=-5;
            int z=-5;


            for (int i = c; i <list.size() ; i++) {
                String temp[] = list.get(i).split(",");
                int q=Integer.parseInt(temp[1]);
                if (temp[0].equals("S")) {
                    t = playSong(q,"playlist");
                }
                else if (temp[0].equals("P")) {
                    z = playPodcast(q,"playlist");
                }

                if(t== (q-2)|| z==(q-2)){
                    i-=2;
                }
                else if (t == -12 || z == -12) {
                    return 0;
                }

                else if (t != -12 && z!=-12 && (t != (q - 2) || z != (q - 2)) && t != q && z != q) {
                    if (t == -8) {
                        return -1;
                    }
                    if (z == -8) {
                        return -1;
                    }
                }
                count=i;
            }
            if (count==count2-1){
                System.out.println("Playlist Ended or No More Song/Episode present in the List");
                return -20;

            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("playsongthroughplaylist");
        }
        return -5;
    }

    //*****************************  display playlist Item according to id ***************************************
    public static int displayPlaylistItem(Statement st,String str,int id){
        ResultSet rs=null;
        int count=0;
        try {
            rs=st.executeQuery("select songid,podcastid from userPlaylist where playlistname='"+str+"' and userid="+id);
            List<String> list=new ArrayList<>();

            System.out.printf(" %-30s\t%-20s\t%-6s\t%6s  \n", "Song/Podcast", "Artist/Host", "Duration/", "SongId/");
            System.out.printf(" %-30s\t%-20s\t%-6s\t%6s  \n", "            ", "           ", "Episode No", " EpisodeId");
            while (rs.next()) {
                Integer car = rs.getInt(1);//songI
                Integer car2 = rs.getInt(2);//podxasI
                if (car == 0 && car2 == 0) {
                    count++;
                    continue;
                }
                if (car == 0) {
                    count++;
                    displayPodcast(rs.getInt(2));
                    //list.add("P," + rs.getInt(2));
                }
                else if (car2 == 0) {
                    count++;
                    displaySong(rs.getInt(1));
                   // list.add("S," + rs.getInt(1));
                }
            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("display playlist Item according to id");
        }
        return count;
    }

    // **************************************    Display song according to song id        ***********************************************************

        public static void displaySong(int id){
            //Class.forName("com.mysql.cj.jdbc.Driver");

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
                Statement st=con.createStatement();
                System.out.println("");
                ResultSet rs1 = st.executeQuery("select * from musictable where songid= " + id);
                rs1.next();
                    System.out.printf(" %-30s\t%-20s\t%-6s\t%6s  \n", rs1.getString(4), rs1.getString(2), rs1.getString(5), rs1.getInt(1));
            }
            catch (SQLException e){
                System.out.println(e);
                System.out.println("Display song according to song id");
            }

        }
        // ************************************     Display Episode according to song id       ********************************************************************
        public static void displayPodcast(int id){
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
                Statement st=con.createStatement();
                System.out.println("");
                ResultSet rs1 = st.executeQuery("select * from podcasttable where episodeid=" + id);
                rs1.next();
                    System.out.printf(" %-30s\t%-20s\t%-6s\t%6s  \n", rs1.getString(3), rs1.getString(2), rs1.getInt(6), rs1.getInt(5));
            }
            catch (SQLException e){
                System.out.println(e);
                System.out.println("Display Episode according to song id ");
            }

        }

        // ******************** MAKING METHOD FOR USERNAME CHECK  ***************************************
    public static String checkUserName(){
        String pname="";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st=con.createStatement();
            String stat5 = "off";

            while (!stat5.equals("on")) {
                System.out.println("");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Enter Name of the Playlist");
                pname = t.next();
                ResultSet rs2 = st.executeQuery("select count(playlistname) from userplaylist where playlistname='" + pname+"'");
                rs2.next();
                if (rs2.getInt(1) != 0) {
                    stat5 = "on";
                    break;
                }
                System.out.println("Playlist don't Exists with this Name");
                System.out.println("EXIT => 0  |  Enter Any number to continue");
                byte x8=t.nextByte();
                if (x8 == 0) {
                    stat5 = "on";
                    break;
                }

            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("MAKING METHOD FOR USERNAME CHECK");
        }
        return pname;
    }

    // ******************** MAKING METHOD FOR SONGID CHECK  ***************************************
    public static int checkSongID(){
        int songid =-2;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st=con.createStatement();
            String stat5 = "off";

            while (!stat5.equals("on")) {
                System.out.println();
                System.out.println("Enter SongId to play the Song ");
                songid = t.nextInt();
                ResultSet rs3=st.executeQuery("select count(songid) from musictable where songid="+songid);
                rs3.next();
                System.out.println(rs3.getInt(1));
                //rs2.next();
                if (rs3.getInt(1)!=0){
                    stat5 = "on";
                    break;
                }
                System.out.println("No Song with this ID");

            }

        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("MAKING METHOD FOR USERNAME CHECK");
        }
        return songid;
    }

    // ******************** MAKING METHOD FOR EPISODEID CHECK  ***************************************
    public static int checkEpisodeID(){
        int episodeid =-2;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st=con.createStatement();
            String stat5 = "off";

            while (!stat5.equals("on")) {
                System.out.println();
                System.out.println("Enter EpisodeID to play the Episode ");
                episodeid = t.nextInt();
                ResultSet rs3=st.executeQuery("select count(episodeid) from podcasttable where episodeid="+episodeid);
                rs3.next();
                System.out.println(rs3.getInt(1));
                //rs2.next();
                if (rs3.getInt(1)!=0){
                    stat5 = "on";
                    break;
                }
                System.out.println("No Episode with this ID");

            }

        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("MAKING METHOD FOR EPISODEID CHECK");
        }
        return episodeid;
    }

    //**************************   CHECK FOR THE PLAYLIST SONG ID  **************************
    public static int checkPlayistSongID(String playlistname,int id) {
        int songid =-2;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st = con.createStatement();
            String stat5 = "off";

            while (!stat5.equals("on")) {
                System.out.println("");
                System.out.println("Enter SongId");
                songid = t.nextInt();
                ResultSet rs2 = st.executeQuery("select count(songid) from userplaylist where userid="+id+" and playlistname='" + playlistname + "' and songid=" + songid);
                rs2.next();
                if (rs2.getInt(1) == 0) {
                    stat5 = "on";
                    break;
                }
                System.out.println("Song Already Exists in the play list");

            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("CHECK FOR THE PLAYLIST SONG ID");
        }
        return songid;
    }

    //**************************   CHECK FOR THE PLAYLIST SONG ID  **************************
    public static int checkPlayistPodcastID(String playlistname,int id) {
        int episodeid =-2;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st = con.createStatement();
            String stat5 = "off";

            while (!stat5.equals("on")) {
                System.out.println("");
                System.out.println("Enter EpisodId");
                episodeid = t.nextInt();
                ResultSet rs2 = st.executeQuery("select count(podcastid) from userplaylist where userid="+id+" and playlistname='" + playlistname + "' and podcastid=" + episodeid);
                rs2.next();
                if (rs2.getInt(1) == 0) {
                    stat5 = "on";
                    break;
                }
                System.out.println("Episode Already Exists in the play list");

            }
        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println("CHECK FOR THE PLAYLIST SONG ID");
        }
        return episodeid;
    }

    // ***********************  MAIN  ********************************************************
    public static void main(String[] args) {
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Jukebox", "root", "root@123");
            Statement st = con.createStatement();

            String status = "invalid";
            int tempuserid = 0;
            String condition="active";
            while(!condition.equals("inactive")) {
                System.out.println("");
                System.out.println("");
                System.out.println("\n" +
                        "                       888888          888               888888b.                     \n" +
                        "                         \"88b          888               888  \"88b                    \n" +
                        "                          888          888               888  .88P                    \n" +
                        "                          888 888  888 888  888  .d88b.  8888888K.   .d88b.  888  888 \n" +
                        "                          888 888  888 888 .88P d8P  Y8b 888  \"Y88b d88\"\"88b `Y8bd8P' \n" +
                        "                          888 888  888 888888K  88888888 888    888 888  888   X88K   \n" +
                        "                          88P Y88b 888 888 \"88b Y8b.     888   d88P Y88..88P .d8\"\"8b. \n" +
                        "                          888  \"Y88888 888  888  \"Y8888  8888888P\"   \"Y88P\"  888  888 \n" +
                        "                        .d88P                                                         \n" +
                        "                      .d88P\"                                                          \n" +
                        "                     888P\"                                                            \n" +
                        "                                                                                      \n" +
                        "                                                                                      \n" +
                        "                                                                                      \n" +
                        "                                                                                      \n");
                System.out.println("");
                System.out.println("");
                System.out.println("                   1 => Login\t\t\t2 => Sign Up\t\t\t0 => Exit");
                System.out.println("Enter Your choice:");
                byte n1 = t.nextByte();
                switch (n1) {
                    case 1:
                        System.out.println("");
                        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                        System.out.println("                                      LOGIN PAGE");
                        System.out.println("Enter UserName");
                        String uname = t.next();
                        System.out.println("Password");
                        String password=t.next();

                        ResultSet rs = st.executeQuery("select * from user");
                        while (rs.next()) {
                            if (rs.getString(2).equals(uname) && rs.getString(5).equals(password)) {
                                tempuserid = rs.getInt(1);
                                System.out.println("LOGIN SUCCESSGULL......");
                                status = "valid";
                            }
                        }
                        if (status == "invalid") {
                            System.out.println(" Credentials Don't Match, Enter Again");
                            break;
                        }
                        String condition1 = "active";
                        while (!condition1.equals("inactive")) {
                            System.out.println("");
                            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                            System.out.println("                                         WELCOME");
                            System.out.println("        1 => Search For Song\t\t2 => Search For Podcast\t\t3 => Your Playlists");
                            System.out.println("                         4 => Back\t\t0 => Exit the Program");
                            System.out.println("Enter Your choice:");
                            System.out.println("");
                            byte n2 = t.nextByte();
                            switch (n2) {
                                case 1:
                                    String condition2 = "active";
                                    while (!condition2.equals("inactive")) {
                                        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                        System.out.println("          1 => Search for All Songs\t\t2 => Search Single song\t\t4 => PLAYSONG");
                                        System.out.println("                           3 => Back\t\t0 => Exit the program");
                                        System.out.println("Enter Your choice:");
                                        byte n5 = t.nextByte();
                                        if (n5 == 1) {
                                            System.out.println("");
                                            System.out.println("Enter choice to Search by : 1-song name | 2-Artist name | 3-album name | 4-Rating | 5-Duration");
                                            byte n7 = t.nextByte();
                                            switch (n7) {
                                                case 1:
                                                    displayAllSong(st, "songname");
                                                    break;
                                                case 2:
                                                    displayAllSong(st, "artistname");
                                                    break;
                                                case 3:
                                                    displayAllSong(st, "albumname");
                                                    break;
                                                case 4:
                                                    displayAllSong(st, "rating");
                                                    break;
                                                case 5:
                                                    displayAllSong(st, "duration");
                                                    break;
                                                default:
                                                    System.out.println("Invalid Entry");
                                            }

                                        }
                                        if (n5 == 2) {
                                            searchForSong(st);
                                        }
                                        if (n5 == 3) {
                                            condition2 = "inactive";
                                            break;
                                        }
                                        if (n5 == 0) {
                                            condition2 = "inactive";
                                            condition1 = "inactive";
                                            condition = "inactive";
                                            break;
                                        }
                                        if (n5 == 4) {
                                            displayAllSong(st, "songid");
                                            int songid=checkSongID();
                                            ResultSet rs2=st.executeQuery("select count(songid) from musictable");
                                            rs2.next();
                                            int count=rs2.getInt(1);
                                            int count2=0;
                                            for (int i = songid; i <=rs2.getInt(1) && i!=0 ; i++) {
                                                i=playSong(i,"normal");
                                                if(i==0)
                                                {
                                                    break;
                                                }
                                                //System.out.println(i);
                                                count2=i;
                                            }
                                            if (count2==count){
                                                System.out.println("No More Song present in the List");
                                            }

                                            break;
                                        }
                                        byte x1=-1;
                                        while (x1!=0) {
                                            System.out.println("2 => BACK |   1 => PLAYSONG  |  0 => EXIT ");
                                             x1= t.nextByte();
                                            if (x1 == 0) {
                                                condition2 = "inactive";
                                                condition1 = "inactive";
                                                condition = "inactive";
                                                break;
                                            } else if (x1 == 1) {
                                                int songid=checkSongID();
                                                ResultSet rs2=st.executeQuery("select count(songid) from musictable");
                                                rs2.next();
                                                int count=rs2.getInt(1);
                                                int count2=0;
                                                for (int i = songid; i <=rs2.getInt(1) && i!=0 ; i++) {
                                                    i=playSong(i,"normal");
                                                    if(i==0)
                                                    {
                                                        break;
                                                    }
                                                    //System.out.println(i);
                                                    count2=i;
                                                }
                                                if (count2==count){
                                                    System.out.println("No More Song present");
                                                }
                                                break;
                                            } else if (x1==2) {
                                                break;
                                            } else {
                                                System.out.println("Invalid Entry");
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    String condition3="active";
                                    while(!condition3.equals("inactive")){
                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                    System.out.println("       1 => Search for All Podcast\t\t2 => Search Single Podcast\t\t4 => PLAY PODCAST");
                                    System.out.println("                            3 => Back\t\t0 => Exit the program");
                                    System.out.println("Enter Your choice:");
                                        byte n4 = t.nextByte();
                                    if (n4 == 1) {
                                        System.out.println("");
                                        System.out.println("Enter choice to Search by : 1-Podcast name | 2-Host name | 3-Epidode name | 4-Rating | 5-Duration");
                                        byte n7 = t.nextByte();
                                        switch (n7) {
                                            case 1:
                                                displayAllPodcast(st, "podcastname");
                                                break;
                                            case 2:
                                                displayAllPodcast(st, "artistname");
                                                break;
                                            case 3:
                                                displayAllPodcast(st, "episodeName");
                                                break;
                                            case 4:
                                                displayAllPodcast(st, "episoderating");
                                                break;
                                            case 5:
                                                displayAllPodcast(st, "episodeDuration");
                                                break;
                                            default:
                                                System.out.println("Invalid Entry");
                                        }
                                    }
                                    if (n4 == 2) {
                                        searchForSPodcast(st);
                                    }
                                    if (n4 == 3) {
                                        condition3 = "inactive";
                                        break;
                                    }
                                    if (n4 == 0) {
                                        condition3 = "inactive";
                                        condition1 = "inactive";
                                        condition = "inactive";
                                        break;
                                    }
                                    if (n4 == 4) {
                                        displayAllPodcast(st,"episodeid");
                                        int episodeid=checkEpisodeID();
                                        ResultSet rs2=st.executeQuery("select count(podcastid) from podcasttable");
                                        rs2.next();
                                        int count=rs2.getInt(1);
                                        int count2=0;
                                        for (int i = episodeid; i <=rs2.getInt(1) && i!=0 ; i++) {
                                            count2=i;
                                            i= playPodcast(i,"normal");

                                            if(i==0)
                                            {
                                                break;
                                            }
                                        }
                                        if(count2==count){
                                            System.out.println("No More Podcast present");
                                        }
                                        break;
                                    }

                                        byte x1=-1;
                                        while (x1!=0) {
                                            System.out.println("2 => BACK  |   1 => PLAY PODCAST  |   0 => EXIT");
                                            x1= t.nextByte();
                                            if (x1 == 0) {
                                                condition3 = "inactive";
                                                condition1 = "inactive";
                                                condition = "inactive";
                                                break;
                                            } else if (x1 == 1) {
                                                int episodeid=checkEpisodeID();
                                                ResultSet rs2=st.executeQuery("select count(podcastid) from podcasttable");
                                                rs2.next();
                                                int count=rs2.getInt(1);
                                                int count2=0;
                                                for (int i = episodeid; i <=rs2.getInt(1) && i!=0 ; i++) {
                                                    count2=i;
                                                    i= playPodcast(i,"normal");

                                                    if(i==0)
                                                    {
                                                        break;
                                                    }
                                                }
                                                if(count2==count){
                                                    System.out.println("No More Podcast present");
                                                }
                                                break;
                                            } else if (x1==2) {
                                                break;
                                            } else {
                                                System.out.println("Invalid Entry");
                                            }

                                        }

                                    }
                                    break;
                                case 3:
                                    String condition4="active";
                                    while(!condition4.equals("inactive")) {
                                        userplaylist(tempuserid, st);
                                        System.out.println("");
                                        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                                        System.out.println("                                       PLAYLISTS");
                                        System.out.println("                             1 => Create A new Playlist ");
                                        System.out.println("                             2 => Add Song to Playlist");
                                        System.out.println("                             3 => Add Podcast Episode to playlist");
                                        System.out.println("                             4 => Play the Playlist");
                                        System.out.println("                             5 => Delete Song to Playlist");
                                        System.out.println("                             6 => Delete Podcast Episode to playlist");
                                        System.out.println("                             7 => Back");
                                        System.out.println("                             8 => Display playlist Song/Podcast episode");
                                        System.out.println("                             0 => Exit the program");
                                        System.out.println("Enter Your choice:");
                                        byte n8 = t.nextByte();
                                        switch (n8) {
                                            case 1:
                                                String stat5="off";
                                                String playlistname="";
                                                while (!stat5.equals("on")) {
                                                    System.out.println("");
                                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                                    System.out.println("Enter Name of the Playlist");
                                                     playlistname=t.next();
                                                    ResultSet rs2 = st.executeQuery("select count(playlistname) from userplaylist where playlistname='" + playlistname+"'");
                                                    rs2.next();
                                                    if (rs2.getInt(1) == 0) {
                                                        stat5="on";
                                                        break;
                                                    }
                                                    System.out.println("Playlist Already Exists");

                                                }
                                                int x2=-5;
                                                String stat3="off";
                                                while (!stat3.equals("on")) {
                                                    System.out.println("1. Add song  ||   2. Add Podcast ");
                                                    x2=t.nextByte();
                                                    switch (x2) {
                                                        case 1:
                                                            String stat9="off";
                                                            int a=3;
                                                            while (!stat9.equals("on")) {
                                                                try {
                                                                    displayAllSong(st, "songid");
                                                                    int songid = checkPlayistSongID(playlistname,tempuserid);
                                                                    a = st.executeUpdate("insert into userplaylist (playlistname,userid,songid) values('" + playlistname + "'," + tempuserid + "," + songid + ")");
                                                                    if (a == 1) {
                                                                        System.out.println("Playlist created");
                                                                    } else {
                                                                        System.out.println("Playlist not Created ");
                                                                    }
                                                                    stat3="on";
                                                                    stat9="on";
                                                                } catch (SQLIntegrityConstraintViolationException e) {
                                                                    System.out.println("There is no Song with this ID");
                                                                }
                                                            }
                                                            break;
                                                        case 2:
                                                            String stat10="off";
                                                            a=3;
                                                            while (!stat10.equals("on")) {
                                                                try {
                                                                    displayAllPodcast(st, "episodeid");
                                                                    System.out.println("Enter EpisodId");
                                                                    int episodeid = checkPlayistPodcastID(playlistname,tempuserid);
                                                                    a = st.executeUpdate("insert into userplaylist (playlistname,userid,podcastid) values('" + playlistname + "'," + tempuserid + "," + episodeid + ")");
                                                                    if (a == 1) {
                                                                        System.out.println("Playlist created");
                                                                    } else {
                                                                        System.out.println("Playlist not Created ");
                                                                    }
                                                                    stat3="on";
                                                                    stat10="on";
                                                                } catch (SQLIntegrityConstraintViolationException e) {
                                                                    System.out.println("There is no Episode with this ID");
                                                                }
                                                            }

                                                            break;
                                                        default:
                                                            System.out.println("Invalid Entry");

                                                    }
                                                }
                                                break;
                                            case 2:
                                                playlistname = checkUserName();
                                                String stat9="off";
                                                int a=3;
                                                while (!stat9.equals("on")) {
                                                    try {
                                                        displayAllSong(st,"songid");
                                                        int songid = checkPlayistSongID(playlistname,tempuserid);
                                                         a= st.executeUpdate("insert into userplaylist (playlistname,userid,songid) values('" + playlistname + "'," + tempuserid + "," + songid + ")");
                                                        if (a == 1) {
                                                            System.out.println("Song Added");

                                                        } else {
                                                            System.out.println("Song not Added ");
                                                        }
                                                        stat9="on";
                                                    } catch (SQLIntegrityConstraintViolationException e) {
                                                        System.out.println("There is no Song with this ID");
                                                    }
                                                }
                                                break;
                                            case 3:
                                                playlistname = checkUserName();
                                                String stat11="off";
                                                a=3;
                                                while (!stat11.equals("on")) {
                                                    try {
                                                        displayAllPodcast(st,"episodeid");
                                                        int episodeid = checkPlayistPodcastID(playlistname,tempuserid);
                                                        a = st.executeUpdate("insert into userplaylist (playlistname,userid,podcastid) values('" + playlistname + "'," + tempuserid + "," + episodeid + ")");
                                                        if (a == 1) {
                                                            System.out.println("Podcast Added");
                                                        } else {
                                                            System.out.println("Podcast not Added ");
                                                        }
                                                        stat11="on";
                                                    } catch (SQLIntegrityConstraintViolationException e) {
                                                        System.out.println("There is no Song with this ID");
                                                    }

                                                }
                                                break;
                                            case 4:
                                                playlistname = checkUserName();

                                                String stat4="off";
                                                int e1=-9;
                                                byte x6=-1;

                                                while(!stat4.equals("on")){
                                                    System.out.println("                          YOUR PLAYLIST SONG/PODCAST EPISODE");
                                                    System.out.println();
                                                    displayPlaylistItem(st,playlistname,tempuserid);
                                                    System.out.println("Enter 1 => Song  OR  2 => Podcast ");
                                                    byte x3=t.nextByte();
                                                    switch (x3){
                                                        case 1:
                                                            System.out.println("Enter Songid");
                                                            int songid=t.nextInt();
                                                            String stat8="off";
                                                            e1=playThePlaylist(st, playlistname, tempuserid,"S,"+songid);
                                                            if(e1==0){
                                                                stat4="on";
                                                            } else if (e1==-20) {
                                                                while (!stat8.equals("on")) {
                                                                    System.out.println();
                                                                    System.out.println("Exit Back  0 => Yes  |  1 => Replay the Playlist");
                                                                    x6=t.nextByte();
                                                                    switch (x6) {
                                                                        case 0:
                                                                            stat8 = "on";
                                                                            stat4="on";
                                                                            break;
                                                                        case 1:
                                                                            stat8 = "on";
                                                                            displayPlaylistItem(st,playlistname,tempuserid);
                                                                            break;
                                                                        default:
                                                                            System.out.println("Invalid Entry");
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                        case 2:
                                                            System.out.println("Enter EpisodeId");
                                                            int episodeid=t.nextInt();
                                                            stat8="off";
                                                            e1=playThePlaylist(st, playlistname, tempuserid,"P,"+episodeid);
                                                            if(e1==0){
                                                                stat4="on";
                                                            }else if (e1==-20) {
                                                                while (!stat8.equals("on")) {
                                                                    System.out.println();
                                                                    System.out.println("Exit Back  0 => Yes  |  1 => Replay the Playlist");
                                                                    x6=t.nextByte();
                                                                    switch (x6) {
                                                                        case 0:
                                                                            stat8 = "on";
                                                                            stat4="on";
                                                                            break;
                                                                        case 1:
                                                                            stat8 = "on";
                                                                            displayPlaylistItem(st,playlistname,tempuserid);
                                                                            break;
                                                                        default:
                                                                            System.out.println("Invalid Entry");
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            System.out.println("Invalid Entry");
                                                    }
                                                }
                                                break;
                                            case 5:
                                                playlistname = checkUserName();
                                                System.out.println("                          YOUR PLAYLIST SONG/PODCAST EPISODE");
                                                System.out.println();
                                                displayPlaylistItem(st,playlistname,tempuserid);
                                                System.out.println("Enter Songid");
                                                int songid = t.nextInt();
                                                a = st.executeUpdate("delete from userplaylist where userid=" + tempuserid + " and playlistname='" + playlistname + "' and songid=" + songid);
                                                if (a == 1) {
                                                    System.out.println("Song Delted");
                                                } else {
                                                    System.out.println("Song not Deleted ");
                                                    System.out.println("Maybe Song was not there");
                                                }
                                                break;
                                            case 6:
                                                playlistname = checkUserName();
                                                System.out.println("                          YOUR PLAYLIST SONG/PODCAST EPISODE");
                                                System.out.println();
                                                displayPlaylistItem(st,playlistname,tempuserid);
                                                System.out.println("Enter Podcast EpisodeID");
                                                int episodeid = t.nextInt();
                                                a = st.executeUpdate("delete from userplaylist where userid=" + tempuserid + " and playlistname='" + playlistname + "' and podcastid=" + episodeid);
                                                if (a == 1) {
                                                    System.out.println("Episode Delted");
                                                } else {
                                                    System.out.println("Episode not Deleted ");
                                                    System.out.println("Maybe Episode was not there");
                                                }
                                                break;
                                            case 7:
                                                condition4="inactive";
                                                break;
                                            case 0:
                                                condition4="inactive";
                                                condition1="inactive";
                                                condition="inactive";
                                                break;
                                            case 8:
                                                playlistname = checkUserName();
                                                displayPlaylistItem(st,playlistname,tempuserid);
                                                break;
                                            default:
                                                System.out.println("Invalid Entry");
                                        }
                                    }
                                    break;
                                case 4:
                                    condition1 = "inactive";
                                    break;
                                case 0:
                                    condition = "inactive";
                                    condition1 = "inactive";
                                    break;
                                default:
                                    System.out.println("Invalid Entry");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("");
                        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                        System.out.println("                                      SIGN UP  PAGE");
                        String stat6="off";
                        String usname ="";
                        while (!stat6.equals("on")) {
                            System.out.println("Enter UserName");
                            usname = t.next();
                            /*if(usname=="\n"){
                                System.out.println("Invalid Entry , Enter Again");
                                continue;
                            }*/
                            ResultSet rs4 = st.executeQuery("select count(userName) from user where userName='"+ usname+"'");
                            rs4.next();
                            if (rs4.getInt(1)==0){
                                stat6="on";
                                break;

                            }
                            else {
                                System.out.println("UserName Already Exists");
                            }
                        }
                        System.out.println("Your Name");
                        String name = t.next();
                        System.out.println("Enter Phone number");
                        String pno = t.next();
                        System.out.println("Password");
                        String pass = t.next();
                        int check = st.executeUpdate("insert into `user` (username,name,phoneno,password) values('" + usname + "','" + name + "','" + pno + "','" + pass + "')");
                        if (check == 1) {
                            System.out.println(":::::::::::::::::::   Signup Successful, You are Registered    :::::::::::::::::::::");
                        } else {
                            System.out.println("Signup Unsuccessful, You are not Registered");
                        }
                        System.out.println("");
                        userSearch(pno, st);
                        byte x5=-1;
                        String stat7="off";
                        while (!stat7.equals("on")) {
                            System.out.println();
                            System.out.println("Exit the Program  0 => Yes  |  1 => No  Go the Main Page");
                            x5=t.nextByte();
                            switch (x5) {
                                case 0:
                                    stat7 = "on";
                                    condition = "inactive";
                                    break;
                                case 1:
                                    stat7 = "on";
                                    break;
                                default:
                                    System.out.println("Invalid Entry");
                            }
                        }
                        break;
                    case 0:
                        condition="inactive";
                        break;
                    default:
                        System.out.println("Invalid Entry");

                }

            }

        }
        catch (SQLException e){
            System.out.println(e);
            System.out.println(" Problem at the end  in SQLEXCEPTION");
        }
        catch (ClassNotFoundException ex){
            System.out.println(ex);
            System.out.println(" Problem at the end  in Classnotfound");

        }

    }

}

