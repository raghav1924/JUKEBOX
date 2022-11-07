import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    Main m=null;

    @Before
    public void setUp(){
        m=new Main();
    }
    @After
    public void tearDown(){
        m=null;
    }
    @Test
    public void checkForAllSongCount(){
        assertEquals(6,m.displayAllSong(m.getConnectionStatment(),"songid"));
    }
    @Test
    public void checkForAllPodcastCount(){
        assertEquals(14,m.displayAllPodcast(m.getConnectionStatment(),"episodeid"));
    }
    @Test
    public void searchForSong(){
        assertEquals(1,m.searchInSong(4,m.getConnectionStatment(),"RAJA"));
        assertEquals(0,m.searchInSong(4,m.getConnectionStatment(),"qwerre"));
    }
    @Test
    public void searchForPodcast(){
        assertEquals(1,m.searchInPodcast(11,m.getConnectionStatment(),"war"));
        assertEquals(8,m.searchInPodcast(11,m.getConnectionStatment(),"the"));
        assertEquals(0,m.searchInPodcast(11,m.getConnectionStatment(),"qwewqrwtreyeryere"));
    }
    @Test
    public void countOfItemInPlaylist(){
        assertEquals(3,m.displayPlaylistItem(m.getConnectionStatment(),"Mine",1));
        assertEquals(4,m.displayPlaylistItem(m.getConnectionStatment(),"Minepod",1));
    }
}
