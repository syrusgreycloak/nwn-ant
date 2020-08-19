package org.progeeks.nwn.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author karlp
 */
public class CompileTaskTest {
    
    public CompileTaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsDisassemblePRC() {
        System.out.println("getFlagsDisassemblePRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setDisassemble(true);
        
        String expected = "-dg";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsEnableExtendsionsPRC() {
        System.out.println("getFlagsEnableExtensionsPRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setEnableExtensions(true);
        
        String expected = "-eg";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsOptimizeScriptsPRC() {
        System.out.println("getFlagsOptimizeScriptsPRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setOptimizeScript(true);
        
        String expected = "-og";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSilenceMsgsPRC() {
        System.out.println("getFlagsSilenceMsgsPRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setSilenceMsgs(true);
        
        String expected = "-qg";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsGenerateDebugPRC() {
        System.out.println("getFlagsGenerateDebugPRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setGenerateDebugFiles(true);
        
        String expected = null;
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSetAllPRC() {
        System.out.println("getFlagsSetAllPRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();
        instance.setDisassemble(true);
        instance.setEnableExtensions(true);
        instance.setOptimizeScript(true);
        instance.setSilenceMsgs(true);
        instance.setStrictMode(true);
        instance.setGenerateDebugFiles(true);
        
        // set the nwnsc variables, but we should not get flags for them
        instance.setShowIncludeSource(true);
        instance.setLoadBaseGameRes(true);
        instance.setVerboseMsgs(true);
        instance.setSuppressWarnings(true);
        instance.setContinueOnError(true);
        instance.setCreateMakeDependencies(true);
        instance.setDisableQuoteParsing(true);
        
        String expected = "-deoqs";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSetNonePRC() {
        System.out.println("getFlagsSetNonePRC");
        String compName = "nwnnsscomp.exe";
        
        CompileTask instance = new CompileTask();

        String expected = "-g";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of buildArgs method, of class CompileTask.
     */
    @Test
    public void tesBuildArgsPRC() {
        System.out.println("buildArgsPRC");
        String includes = "d:\\NWN\\NeverwinterCampaignModule\\nss";
        File targetDir = new File("d:\\NWN\\NeverwinterCampaignModule\\build\\ncs");
        
        CompileTask instance = new CompileTask();
        instance.setCompiler(new File(System.getenv("NWN_UTIL")+"\\nwnnsscomp.exe"));
        instance.setOptimizeScript(true);
        instance.setVersion("1.69");

        List<String> expected = new ArrayList<String>();
        expected.add("-og");
        expected.add("-i " + includes);
        expected.add("-v1.69");
        
        List<String> result = instance.buildArgList(targetDir, includes);
        
        assertEquals(expected.size(), result.size());
        
        for (int i = 0; i < result.size(); i++) {
            String resStr = result.get(i);
            String expStr = expected.get(i);
            
            assertEquals(expStr, resStr);
        }
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsDisassembleNWNSC() {
        System.out.println("getFlagsDisassembleNWNSC");
        String compName = "nwnsc.exe";
        
        CompileTask instance = new CompileTask();
        instance.setDisassemble(true);
        
        String expected = "-d";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsEnableExtensionsNWNSC() {
        System.out.println("getFlagsEnableExtensionsNWNSC");
        String compName = "nwnsc.exe";
        
        CompileTask instance = new CompileTask();
        instance.setEnableExtensions(true);
        
        String expected = "-e";
        String result = instance.getFlags(compName);
        
        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsOptimizeScriptsNWNSC() {
        System.out.println("getFlagsOptimizeScriptsNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setOptimizeScript(true);

        String expected = "-o";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSilenceMsgsNWNSC() {
        System.out.println("getFlagsSilenceMsgsNWNSC");
        String compName = "nwnnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setSilenceMsgs(true);

        String expected = "-q";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsGenerateDebugNWNSC() {
        System.out.println("getFlagsGenerateDebugNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setGenerateDebugFiles(true);

        String expected = "-g";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsShowIncludesNWNSC() {
        System.out.println("getFlagsShowIncludesNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setShowIncludeSource(true);

        String expected = "-j";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsLoadBaseNoInstallDirNWNSC() {
        System.out.println("getFlagsLoadBaseNoInstallDirNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setLoadBaseGameRes(true);

        String expected = "-l";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsLoadBaseInstallDirNWNSC() {
        System.out.println("getFlagsLoadBaseInstallDirNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setInstallDir(new File("d:\\SteamLibrary\\steamapps\\common\\Neverwinter Nights"));
        instance.setLoadBaseGameRes(true);

        String expected = null;
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsVerboseMsgsNWNSC() {
        System.out.println("getFlagsVerboseMsgsNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setVerboseMsgs(true);

        String expected = "-v";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSuppressWarningsNWNSC() {
        System.out.println("getFlagsSuppressWarningsNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setSuppressWarnings(true);

        String expected = "-w";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsContinueOnErrorNWNSC() {
        System.out.println("getFlagsContinueOnErrorNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setContinueOnError(true);

        String expected = "-y";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsCreateMakefileDependenciesNWNSC() {
        System.out.println("getFlagsCreateMakefileDependenciesNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setCreateMakeDependencies(true);

        String expected = "-M";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsDisableQuoteParsingNWNSC() {
        System.out.println("getFlagsDisableQuoteParsingNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setDisableQuoteParsing(true);

        String expected = "-Q";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSetAllNWNSC() {
        System.out.println("getFlagsSetAllNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();
        instance.setDisassemble(true);
        instance.setEnableExtensions(true);
        instance.setOptimizeScript(true);
        instance.setSilenceMsgs(true);
        instance.setStrictMode(true);
        instance.setGenerateDebugFiles(true);

        // set the nwnsc variables
        instance.setShowIncludeSource(true);
        instance.setLoadBaseGameRes(true);
        instance.setVerboseMsgs(true);
        instance.setSuppressWarnings(true);
        instance.setContinueOnError(true);
        instance.setCreateMakeDependencies(true);
        instance.setDisableQuoteParsing(true);

        String expected = "-deoqsgjlvwyMQ";
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of getFlags method, of class CompileTask.
     */
    @Test
    public void testGetFlagsSetNoneNWNSC() {
        System.out.println("getFlagsSetNoneNWNSC");
        String compName = "nwnsc.exe";

        CompileTask instance = new CompileTask();

        String expected = null;
        String result = instance.getFlags(compName);

        assertEquals(expected, result);
    }

    /**
     * Test of buildArgs method, of class CompileTask.
     */
    @Test
    public void tesBuildArgsNWNSC() {
        System.out.println("buildArgsNWNSC");
        String includes = "d:\\NWN\\NeverwinterCampaignModule\\nss";
        File targetDir = new File("d:\\NWN\\NeverwinterCampaignModule\\build\\ncs");

        CompileTask instance = new CompileTask();
        instance.setCompiler(new File("d:\\NWN\\NwnSC\\nwnsc.exe"));
        instance.setTargetDir(targetDir);
        File homeDir = new File("c:\\Users\\karlp\\OneDrive\\Documents\\Neverwinter Nights");
        instance.setHomeDir(homeDir);
        File installDir = new File("d:\\SteamLibrary\\steamapps\\common\\Neverwinter Nights");
        instance.setInstallDir(installDir);
        instance.setOptimizeScript(true);
        instance.setGenerateDebugFiles(true);
        instance.setVersion("1.69");
        instance.setErrorPrefix("CustomErrorPrefix");

        List<String> expected = new ArrayList<String>();
        expected.add("-og");
        expected.add("-i " + includes);
        expected.add("-b " + targetDir.getAbsolutePath());
        expected.add("-h " + homeDir.getAbsolutePath());
        expected.add("-n " + installDir.getAbsolutePath());
        expected.add("-m 1.69");
        expected.add("-x CustomErrorPrefix");

        List<String> result = instance.buildArgList(targetDir, includes);

        assertEquals(expected.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            String resStr = result.get(i);
            String expStr = expected.get(i);

            assertEquals(expStr, resStr);
        }
    }

}
