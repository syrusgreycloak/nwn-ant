/*
 * $Id: CompileTask.java,v 1.4 2005/06/01 12:18:57 pwwisnes Exp $
 *
 * Copyright (c) 2004, Paul Speed
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1) Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2) Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3) Neither the names "Progeeks", "NWN Tools", nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.progeeks.nwn.ant;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.util.*;

import org.progeeks.util.ErrorInfo;
import org.progeeks.util.ErrorListener;

import org.progeeks.nwn.model.ScriptCompiler;

/**
 * ANT task for executing a commandline NWscript compiler. Right now I plan to
 * support Bioware's but it should be easy to support PRC/Torlack's as well.
 * Actually, both tools are a little kludgy on the command line options but at
 * least the PRC compiler lets us not generate debug info and we can specify an
 * include path.
 *
 * @version $Revision: 1.4 $
 * @author Paul Speed
 */
public class CompileTask extends MatchingTask {
    
    private static final String COMPILER_BIOWARE = "clcompile";
    private static final String COMPILER_PRC = "nwnnsscomp";
    public static final String COMPILER_NWNSC = "nwnsc";

    private int threadCount = 10;
    // compiler flags
    private boolean disassemble = false;            // -d
    private boolean enableExtensions = false;       // -e
    private boolean generateDebugFiles = false;     // -g
    private boolean optimizeScript = false;         // -o
    private boolean silenceMsgs = false;            // -q
    private boolean strictMode = false;             // -s
    
    // nwnsc compiler flags
    private boolean showIncludeSource = false;      // -j
    private boolean loadBaseGameRes = false;        // -l
    private boolean verboseMsgs = false;            // -v
    private boolean suppressWarnings = false;       // -w
    private boolean continueOnError = false;        // -y
    private boolean createMakeDependencies = false; // -M
    private boolean disableQuoteParsing = false;    // -Q
    
    // nwnsc options
    private File homeDir;
    private File installDir;
    private String errorPrefix;
    
    // common options
    private String pathspec;
    private String version;
    private File outfile;
    
    private File compiler;
    private File sourceDir;
    private File targetDir;

    private ScriptCompiler scriptCompiler = new ScriptCompiler();
    private ErrorObserver errorObserver = new ErrorObserver();

    public void setDisassemble(boolean disassemble) {
        this.disassemble = disassemble;
    }

    public void setEnableExtensions(boolean enableExtensions) {
        this.enableExtensions = enableExtensions;
    }

    public void setGenerateDebugFiles(boolean generateDebugFiles) {
        this.generateDebugFiles = generateDebugFiles;
    }

    public void setOptimizeScript(boolean optimizeScript) {
        this.optimizeScript = optimizeScript;
    }

    public void setSilenceMsgs(boolean silenceMsgs) {
        this.silenceMsgs = silenceMsgs;
    }

    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }

    public void setShowIncludeSource(boolean showIncludeSource) {
        this.showIncludeSource = showIncludeSource;
    }

    public void setLoadBaseGameRes(boolean loadBaseGameRes) {
        this.loadBaseGameRes = loadBaseGameRes;
    }

    public void setVerboseMsgs(boolean verboseMsgs) {
        this.verboseMsgs = verboseMsgs;
    }

    public void setSuppressWarnings(boolean suppressWarnings) {
        this.suppressWarnings = suppressWarnings;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public void setCreateMakeDependencies(boolean createMakeDependencies) {
        this.createMakeDependencies = createMakeDependencies;
    }

    public void setDisableQuoteParsing(boolean disableQuoteParsing) {
        this.disableQuoteParsing = disableQuoteParsing;
    }

    public void setHomeDir(File homeDir) {
        this.homeDir = homeDir;
    }

    public void setInstallDir(File installDir) {
        this.installDir = installDir;
    }

    public void setErrorPrefix(String errorPrefix) {
        this.errorPrefix = errorPrefix;
    }

    public void setPathspec(String pathspec) {
        this.pathspec = pathspec;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOutfile(File outfile) {
        this.outfile = outfile;
    }

    public void setSourceDir(File sourceDir) {
        this.sourceDir = sourceDir;
    }

    public void setTargetDir(File targetDir) {
        this.targetDir = targetDir;
    }

    public void setCompiler(File compiler) {
        this.compiler = compiler;
    }

    public void execute() throws BuildException {
        if (sourceDir == null) {
            throw new BuildException("No sourcedir attribute specified.");
        }
        if (targetDir == null) {
            throw new BuildException("No targetdir attribute specified.");
        }
        Path compilerPath = compiler.toPath();
        
        if (scriptCompiler.hasCompiler(compilerPath)) {
            scriptCompiler.setCompilerPath(compilerPath);
        } else if (!scriptCompiler.hasCompiler()) {
            throw new BuildException("Can't locate NWN compiler on path or at:" + compiler);
        }

        scriptCompiler.setMaxCompilerCount(threadCount);

        log("Using compiler: " + scriptCompiler.getCompilerPath(), Project.MSG_VERBOSE);
        System.out.println("Using compiler: " + scriptCompiler.getCompilerPath());
        log("Executing commandline compiler", Project.MSG_VERBOSE);

        DirectoryScanner ds = getDirectoryScanner(sourceDir);
        String[] files = ds.getIncludedFiles();

        String includes = getIncludes(files);
        log("Includes: " + includes, Project.MSG_VERBOSE);

        File[] scripts = getNewFiles(sourceDir, targetDir, files);
        if (scripts.length == 0) {
            return;
        }

        log("Compiling " + scripts.length + " scripts to: " + targetDir);

        for (int i = 0; i < scripts.length; i++) {
            // Need to figure out the destination path from the
            // source path.
            File targetFile = getTargetFile(scripts[i]);
            File targetPath = targetFile.getParentFile();

            // Make sure the directory exists
            if (!targetPath.exists() && !targetPath.mkdirs()) {
                throw new BuildException("Error creating target directory: " + targetPath);
            }

            log(scripts[i].toString(), Project.MSG_VERBOSE);

            // Now run the compile
            compileSource(targetPath, scripts[i], targetFile, includes);
        }

        try {
            scriptCompiler.waitForAll();

            int count = scriptCompiler.getIncludeCount();
            if (count > 0) {
                log("Skipped " + count + " include files.");
            }
            count = scriptCompiler.getCompiledCount();
            if (count > 0) {
                log(count + " scripts compiled successfully.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void compileSource(File targetDir, File source, File target, String includes) throws BuildException {
//        String[] args = new String[]{"-i", includes, "-g"};
        List<String> args = buildArgList(targetDir, includes);
        scriptCompiler.compileScript(source, args, targetDir, errorObserver);
    }
    
    protected List<String> buildArgList(File targetDir, String includes) {
        List<String> args = null;
        String compName = compiler.toPath().getFileName().toString();
        
        // bioware compiler takes no switch arguments
        if (!compName.contains(COMPILER_BIOWARE)) {
            args = new ArrayList<>();
            
            // common arguments
            String flags = getFlags(compName);
            if (null != flags) {
                args.add(flags);
            }
            
            // pathspec/includes
            if (null != includes && !includes.trim().isEmpty()) {
                args.add("-i");
                args.add(includes);
            }
            
            // version of the compiler
            if (compName.contains(COMPILER_PRC)) {
                if (null != version && !version.trim().isEmpty()) {
                    args.add("-v" + version);
                }
            } else {
                // NWNSC compiler switches
                args.add("-b"); 
                args.add(targetDir.getAbsolutePath());
                
                if (null != homeDir) {
                    args.add("-h");
                    args.add(homeDir.getAbsolutePath());
                }
                
                if (null != installDir) {
                    args.add("-n");
                    args.add(installDir.getAbsolutePath());
                }
                
                if (null != version && !version.trim().isEmpty()) {
                    args.add("-m");
                    args.add(version);
                }
                
                if (null != errorPrefix && !errorPrefix.trim().isEmpty()) {
                    args.add("-x");
                    args.add(errorPrefix);
                }
            }
            
            if (args.isEmpty()) {
                args = null;
            }
        }
        
        return args;
    }

    protected String getFlags(String compName) {
        StringBuilder flags = new StringBuilder("-");
        
        // common flags
        if (disassemble) {
            flags.append("d");
        }
        if (enableExtensions) {
            flags.append("e");
        }
        if (optimizeScript) {
            flags.append("o");
        }
        if (silenceMsgs) {
            flags.append("q");
        }
        if (strictMode) {
            flags.append("s");
        }
        // generate debug files
        // switch for prc compiler/nwnnsscomp is to NOT generate files
        // for nwnsc, it is TO GENERATE files
        if ((generateDebugFiles && compName.contains(COMPILER_NWNSC))
           || (!generateDebugFiles && compName.contains(COMPILER_PRC))) {
            flags.append("g");
        }
        
        // nwnsc only flags
        if (compName.contains(COMPILER_NWNSC)) {
            if (showIncludeSource) {
                flags.append("j");
            }
            if (loadBaseGameRes) {
                if (null == installDir) {
                    flags.append("l");
                }
            }
            if (verboseMsgs) {
                flags.append("v");
            }
            if (suppressWarnings) {
                flags.append("w");
            }
            if (continueOnError) {
                flags.append("y");
            }
            if (createMakeDependencies) {
                flags.append("M");
            }
            if (disableQuoteParsing) {
                flags.append("Q");
            }
        }
        
        String flagsStr = flags.toString();
        if (flagsStr.length() == 1) {
            flagsStr = null;
        }
        
        return flagsStr;
    }

    /**
     * Returns the full destination path for the specified source path.
     */
    protected File getTargetFile(File source) {
        // First we need to find the part of the path
        // that is not our srcdir.
        String root = sourceDir.toString();
        String path = source.toString();
        if (path.startsWith(root)) {
            path = path.substring(root.length() + 1);
        }

        return (new File(targetDir, path));
    }

    /**
     * Returns an encoded list of implied include paths based on the specified
     * script list.
     */
    protected String getIncludes(String[] files) {
        // Build up a list of directories that we need to use as potential
        // include paths.  The idea being that the compile task was called
        // with an entire "set" of scripts.  Proper partitioning should
        // be done at the task definition level.
        Set includes = new TreeSet();
        for (int i = 0; i < files.length; i++) {
            //File f = new File( srcdir, files[i] );
            //System.out.println( "File:" + files[i] );
            File f = new File(sourceDir, files[i]);
            //System.out.println( "File:" + f );
            includes.add(f.getParentFile());
        }

        StringBuffer sb = new StringBuffer();
        for (Iterator i = includes.iterator(); i.hasNext();) {
            File f = (File) i.next();
            if (sb.length() != 0) {
                sb.append(";");
            }
            sb.append(f);
        }
        
        if (null != pathspec && !pathspec.trim().isEmpty()) {
            sb.append(";");
            sb.append(pathspec);
        }

        return (sb.toString());
    }

    /**
     * Returns the list of script files that are newer than their compiled
     * counterparts.
     */
    protected File[] getNewFiles(File srcDir, File destDir, String[] files) {
        GlobPatternMapper mapper = new GlobPatternMapper();
        mapper.setFrom("*.nss");
        mapper.setTo("*.ncs");

        SourceFileScanner sourceScanner = new SourceFileScanner(this);
        File[] newFiles = sourceScanner.restrictAsFiles(files, srcDir, destDir, mapper);
        return (newFiles);
    }

    private class ErrorObserver implements ErrorListener {

        public void error(Object source, ErrorInfo error) {
            String s = String.valueOf(source);
            String root = sourceDir.toString();
            if (s.startsWith(root)) {
                s = s.substring(root.length() + 1);
            }
            System.out.println(s + "  " + error);
        }
    }
}
