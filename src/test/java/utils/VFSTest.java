package utils;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * User: elenaandreeva
 * Date: 4/8/14
 * Time: 1:38 PM

 */
public class VFSTest {


    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testGetAbsolutePath() throws Exception {
        String relpath = "server/myfile.txt";
        String abspath = System.getProperty("user.dir")+'/' + relpath;
        Assert.assertEquals(VFS.getAbsolutePath(relpath), abspath);
        Assert.assertEquals(VFS.getAbsolutePath(abspath), abspath);
    }

    @Test
    public void testGetRelativePath() throws Exception {
        String relpath = "server/myfile.txt";
        String abspath = System.getProperty("user.dir")+'/' + relpath;
        Assert.assertEquals(VFS.getRelativePath(relpath), relpath);
        Assert.assertEquals(VFS.getRelativePath(abspath), relpath);
    }

    @Test
    public void testBfs() throws Exception {

        List<File> files = new LinkedList<File>();

        files.add(new File(VFS.getAbsolutePath("src/test/data/index.html")));
        files.add(new File(VFS.getAbsolutePath("src/test/data/bom/bom4.txt")));
        files.add(new File(VFS.getAbsolutePath("src/test/data/bom/bom3.txt")));
        files.add(new File(VFS.getAbsolutePath("src/test/data/bom/bom.txt")));
        Collections.sort(files);
        List<File> files2 = VFS.bfs("src/test/data");
        Collections.sort(files2);


        Assert.assertEquals(files2, files);
        VFS.bfs("/src/test/data/index.html");
        files.clear();
        files.add(new File(VFS.getAbsolutePath("src/test/data/index.html")));
        Assert.assertEquals(VFS.bfs("src/test/data/index.html"), files);

    }

    @Test
    public void testWriteToFile() throws Exception {
        VFS.writeToFile("src/test/data/bom/bom3.txt", "bom3");
        Assert.assertEquals(VFS.readFile("src/test/data/bom/bom3.txt"), "bom3");
        VFS.writeToFile("src/test/test", "smth");
        Assert.assertEquals(VFS.readFile("src/test/test"), new String());

    }

    @Test
    public void testWriteToEndOfFile() throws Exception {
        VFS.writeToFile("src/test/data/bom/bom4.txt", "bom4");
        VFS.writeToEndOfFile("src/test/data/bom/bom4.txt", "!!!");
        Assert.assertEquals(VFS.readFile("src/test/data/bom/bom4.txt"), "bom4!!!");
        VFS.writeToFile("src/test/test", "smth");
        Assert.assertEquals(VFS.readFile("src/test/test"), new String());

    }

    @Test
    public void testReadFile() throws Exception {
        Assert.assertEquals(VFS.readFile("src/test/data/bom/bom.txt"), "bom");
        Assert.assertEquals(VFS.readFile("src/test/data/bom/bom2.txt"), new String());

    }


}
