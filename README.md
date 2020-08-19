# nwn-ant
Updated version of nwn-ant tools by pspeed to allow for specification of compiler to be used instead of defaulting to nwnnsscomp

<h3>Updated options for CompileTask:</h3>
<ul>
  <li><b>compiler</b> - can now accept clcompile, nwnnsscomp, or nwnsc as the compiler to use</li>
  <li><b>srcdir</b> - changed to <b>sourcedir</b></li>
  <li><b>destdir</b> - changed to <b>targetdir</b>. NOTE: this fills in the -b option for nwnsc</li>
</ul>

<h3>New options for CompileTask useable with nwnnsscomp and nwnsc:</h3>
<ul>
<li><b>disassemble</b> - true/false (-d)</li>
<li><b>enableextensions</b> - true/false (-e)</li>
<li><b>generatedebugfile</b> - true/false (-g) NOTE: correctly sets for nwnnsscomp and nwnsc based upon value as the compilers have opposite meanings for the swtich</li>
<li><b>optimizescript</b> - true/false (-o)</li>
<li><b>silencemsgs</b> - true/false (-q)</li>
<li><b>strictmode</b> - true/false (-s)</li>

<li><b>pathspec</b> - semicolon separated list of paths to use to look for include files (script working directory included by default)</li>
<li><b>version</b> - string, NWN version.  (for nwnnsscomp -v# or nwnsc -m switches)</li>
<li><b>outfile</b> - string, output file name</li>
</ul>

<h3>New options for CompileTask usable with nwnsc:</h3>
<ul>
<li><b>showincludesource</b> - true/false (-j)</li>
<li><b>loadbasegameres</b> - true/false (-l) NOTE: ignored if installdir is provided</li>
<li><b>verbosemsgs</b> - true/false (-v)</li>
<li><b>suppresswarngings</b> - true/false (-w)</li>
<li><b>continueonerror</b> - true/false (-y)</li>
<li><b>createmakedependencies</b> - true/false (-M)</li>
<li><b>disablequoteparsing</b> - true/false (-Q)</li>

<li><b>homedir</b> - string, path to user Neverwinter Nights folder (-h)</li>
<li><b>installdir</b> - stirng, path to Neverwinter Nights game folder (-n)  NOTE: if specified, ignores loadbasegameres setting</li>
<li><b>errorprefix</b> - string to add to front of error messages (-x)</li>
</ul>
