<h1>Open File Fast</h1>
<h2>Introduction</h2>
<p><strong>Open File Fast</strong> is a plugin for <a href="http://netbeans.org">Netbeans IDE</a> which allows you to quickly find and open files in your projects.</p>
<p>It is inspired by <a href="http://macromates.com/">Textmate's</a> quick open file dialog. However, it has a lot more features. Basically it works like this: you type only several characters of filename you are searching for and you get list of all files matching your pattern. For example, when you are looking for FooBar.java you can type <em>fbj</em> and you will find it. It really helps navigating through the project (especially bigger ones) as you don't need to use a mouse nor locate a filename visually in project tree but just use filename shortcuts.</p>
<h2>Download</h2>
<p>You can download the plugins from the <a href="https://github.com/sickill/off-plugin/releases">releases page</a>.<br>
The NetBeans plugin can be downloaded from the NetBeans Plugin Portal <a href="http://plugins.netbeans.org/plugin/16495/open-file-fast">&le;8.0</a>, <a href="http://plugins.netbeans.org/plugin/63478/open-file-fast">&ge;8.1</a></p>


<h2>Key features of Open File Fast</h2>
<ul>
	<li>Quick finding and opening any file from selected project</li>
	<li>Two matching modes:
	<ul>
		<li><em>Smart</em> (like in Textmate, matching filenames must contain all typed characters, they can be separated by other characters)</li>
		<li><em>Exact</em> (like Netbeans' Go-To-File, matching filenames must contain exact phrase)</li>
	</ul></li>
	<li>Matching from start of filename or anywhere in filename</li>
	<li>Support for * wildcard in <em>exact</em> mode</li>
	<li>Changing minimum entered pattern length needed for searching (default: 3)</li>
	<li>Changing delay (time from last keystroke) after which searching starts (default: 300ms)</li>
	<li>Optional cleaning of search input field when O-F-F window opens</li>
	<li>Hiding files you don't work on (matching specified regular expresions) from results</li>
	<li>Moving less important files (matching specified regular expresions) to the bottom of results list</li>
	<li>Results sorted by:
	<ul>
		<li>File popularity (files opened frequently at the top of list)</li>
		<li>Match accuracy (distance between characters from search phrase in matched filenames)</li>
	</ul></li>
	<li>Opening multiple files at once (selected with ctrl/shift + mouse)</li>
	<li>Switching between search history entries by CTRL-Up/CTRL-Down</li>
</ul>
<h2>Usage</h2>
<p>Open File Fast plugin installs itself in Netbeans' <i>Navigate</i> menu as <i>Open File Fast</i>. I recommend creating a keyboard shortcut for it in <em>Tools/Options/Keymap/Project</em>.</p>
<p>When invoked, it opens a search dialog. Type <em>some</em> characters from filename you want to find. <em>Some</em> as you may skip any characters you want. Use * as a placeholder for any text. You can narrow down the search to files in specified directory by typing a directory prefix followed by slash and then a filename pattern.</p>
<img src="https://cloud.githubusercontent.com/assets/1857095/13906271/9b8c8004-eed2-11e5-8a16-25e5445eb51f.png">

<p>For example, type:</p>
<ul>
	<li><em>user</em> when looking for <em>user.rb</em> or <em>UberServer.java</em></li>
	<li><em>ush</em> when looking for <em>users_helper.rb</em></li>
	<li><em>*.js</em> when you want to list all javascript files (<em>*</em> is not needed here if "Matching from start of filename" is turned off in preferences)</li>
	<li><em>a/m/</em> when you want to list all files in <em>app/models</em> directory</li>
	<li><em>a/v/s</em> when you are looking for <em>show.html</em> in <em>app/views</em> directory</li>
</ul>
<h2>Configuration</h2>
<p>Open File Fast can be set up to your own preferences and to match your own workflow. You can configure it under <em>Open File Fast</em> tab in Netbeans' <em>Miscellaneous</em> options.</p>
<img src="https://cloud.githubusercontent.com/assets/1857095/13906272/9c5d21d2-eed2-11e5-9d2b-8b1a64219ef4.png">


<h2>Updates</h2>
<h3>1.3.1.0</h3>
<ul>
<li>[<a href="https://github.com/sickill/off-plugin/issues/46">Bugfix</a>]: Fixed issue in PHP projects, where the source dir was not within the project dir (use source roots for filtering instead)</li>
 </ul>

<h3>1.3.0.5</h3>
<ul>
<li>[<a href="https://github.com/sickill/off-plugin/issues/32">Feature</a>]: Allow manual indexing (introduced reindex button)</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/41">Feature</a>]: Switch between search history using CTRL-Up/CTRL-Down</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/26">Feature</a>]: Searching for directories should also start at source-/resource roots of the project</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/39">Feature</a>]: Sort project dropdown </li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/28">Feature</a>]: Icons for project drop down</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/27">Feature</a>]: Add mnemonics to search dialog (improved keyboard support)</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/25">Feature</a>]: Add mnemonics to options dialog (improved keyboard support)</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/31">Task</a>]: Rename action to "Go to File (Fa&st)..." to match the other GoTo actions</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/35">Task</a>]: Add link to github in options/link to plugin portal</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/8">Bugfix</a>]: Fixed: Use selected project from Node/File, when opening search dialog</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/22">Bugfix</a>]: Fixed: Project drop down list, shouldn't be closed after selecting an other project (improved keyboard support)</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/29">Bugfix</a>]: Fixed: Action is enabled, even if there are no open projects</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/30">Bugfix</a>]: Fixed NPE when opening dialog</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/33">Bugfix</a>]: Fixed exceptions when switching projects too fast</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/34">Bugfix</a>]: Fixed NPE when uninstalling plugin</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/38">Bugfix</a>]: Don't disable controls while indexing</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/37">Bugfix</a>]: Fixed: Reopening the dialog with the same project selected triggers reindexing</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/40">Bugfix</a>]: Fixed: Selection border color too light for dark themes</li>
<li>[<a href="https://github.com/sickill/off-plugin/issues/36">Bugfix</a>]: Fixed: Dialog opens on the wrong monitor (dual monitor setup)</li>
 </ul>

<h2>Building from source</h2>
<ul>
	<li>You will need a NetBeans installation that includes the NetBeans Platform SDK.
	<ul>
		<li>This is included by default in the standard Java SE and Java EE variations.</li>
		<li>If you're using the C/C++ or HTML5 &amp; PHP bundles, you will probably need to install the <em>NetBeans Plugin Development</em> plugin.</li>
	</ul></li>
	<li>Clone this repo.</li>
	<li>In NetBeans, go to <em>File</em> – <em>Open Project…</em>, navigate to the <em>netbeans</em> subdir in the repo and open it.
	<ul>
		<li>The dir should have a special icon. If it doesn't, your installation probably doesn't have the <em>NetBeans Plugin Development</em> plugin.</li>
	</ul></li>
	<li>Right click on the project and select <em>Create NBM</em>.</li>
	<li>If all goes well, this should create a <em>net-sickill-off.nbm</em> file in the <em>netbeans/build</em> dir of the repo.</li>
</ul>
<p>Provide defects, request for enhancements and feedback at <a href="https://github.com/sickill/off-plugin/issues">https://github.com/sickill/off-plugin/issues</a></p>
<p>Compatible to >=NB 8.0.2</p>
<p>Legal disclaimer: Code is licensed under MIT</p>
