package net.sickill.off.netbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;

/**
 * https://github.com/sickill/off-plugin/issues/26
 *
 * @author markiewb
 */
public class SourceGroups {

    /**
     * Copied from several sources These constants where not public API, so they
     * are duplicated in here.
     */
    // from org.netbeans.modules.web.api.webmodule
    public static final String WebProjectConstants_TYPE_DOC_ROOT = "doc_root"; //NOI18N
    public static final String WebProjectConstants_TYPE_WEB_INF = "web_inf"; //NOI18N
    // from org.netbeans.modules.web.clientproject.api
    public static final String WebClientProjectConstants_SOURCES_TYPE_HTML5 = "HTML5-Sources"; // NOI18N
    public static final String WebClientProjectConstants_SOURCES_TYPE_HTML5_SITE_ROOT = "HTML5-SiteRoot"; // NOI18N
    public static final String WebClientProjectConstants_SOURCES_TYPE_HTML5_TEST = "HTML5-Tests"; // NOI18N
    public static final String WebClientProjectConstants_SOURCES_TYPE_HTML5_TEST_SELENIUM = "HTML5-Tests-Selenium"; // NOI18N 
    // from org.netbeans.api.project
    public static final String Sources_TYPE_GENERIC = "generic"; // NOI18N 
    // from org.netbeans.modules.maven.groovy
    public static final String GroovySourcesImpl_TYPE_GROOVY = "groovy";
    // from org.netbeans.modules.maven.j2ee 
    public static final String J2eeMavenSourcesImpl_TYPE_DOC_ROOT = "doc_root"; // NOI18N
    public static final String J2eeMavenSourcesImpl_TYPE_WEB_INF = "web_inf";  // NOI18N
    // from org.netbeans.modules.php.api
    public static final String PhpConstants_SOURCES_TYPE_PHP = "PHPSOURCE"; // NOI18N
    // from org.netbeans.modules.maven.classpath
    public static final String MavenSourcesImpl_TYPE_OTHER = "Resources"; //NOI18N
    public static final String MavenSourcesImpl_TYPE_TEST_OTHER = "TestResources"; //NOI18N
    public static final String MavenSourcesImpl_TYPE_GEN_SOURCES = "GeneratedSources"; //NOI18N
    // from javascript.refactoring\src\org\netbeans\modules\refactoring\javascript\\ui\tree\FolderTreeElement.java
    // from refactoring.java\src\org\netbeans\modules\refactoring\java\\ui\tree\FolderTreeElement.java
    public static final String FolderTreeElement_XML = "XML"; //NOI18N
    // from org.netbeans.api.java.project
    public static final String JavaProjectConstants_SOURCES_TYPE_JAVA = "java"; // NOI18N
    public static final String JavaProjectConstants_SOURCES_TYPE_RESOURCES = "resources"; // NOI18N
    public static final String JavaProjectConstants_SOURCES_HINT_MAIN = "main"; //NOI18N
    public static final String JavaProjectConstants_SOURCES_HINT_TEST = "test"; //NOI18N       

    public static Collection<SourceGroup> getAllSourceGroups(Project p) {
        List<SourceGroup> list = new ArrayList<>();
        if (null == p) {
            return list;
        }
        final Sources sources = ProjectUtils.getSources(p);
        list.addAll(Arrays.asList(sources.getSourceGroups(WebProjectConstants_TYPE_DOC_ROOT)));
        list.addAll(Arrays.asList(sources.getSourceGroups(WebProjectConstants_TYPE_WEB_INF)));
        list.addAll(Arrays.asList(sources.getSourceGroups(WebClientProjectConstants_SOURCES_TYPE_HTML5)));
        list.addAll(Arrays.asList(sources.getSourceGroups(WebClientProjectConstants_SOURCES_TYPE_HTML5_SITE_ROOT)));
        list.addAll(Arrays.asList(sources.getSourceGroups(WebClientProjectConstants_SOURCES_TYPE_HTML5_TEST)));
        list.addAll(Arrays.asList(sources.getSourceGroups(WebClientProjectConstants_SOURCES_TYPE_HTML5_TEST_SELENIUM)));
        list.addAll(Arrays.asList(sources.getSourceGroups(Sources_TYPE_GENERIC)));
        list.addAll(Arrays.asList(sources.getSourceGroups(GroovySourcesImpl_TYPE_GROOVY)));
        list.addAll(Arrays.asList(sources.getSourceGroups(J2eeMavenSourcesImpl_TYPE_DOC_ROOT)));
        list.addAll(Arrays.asList(sources.getSourceGroups(J2eeMavenSourcesImpl_TYPE_WEB_INF)));
        list.addAll(Arrays.asList(sources.getSourceGroups(PhpConstants_SOURCES_TYPE_PHP)));
        list.addAll(Arrays.asList(sources.getSourceGroups(MavenSourcesImpl_TYPE_OTHER)));
        list.addAll(Arrays.asList(sources.getSourceGroups(MavenSourcesImpl_TYPE_TEST_OTHER)));
        list.addAll(Arrays.asList(sources.getSourceGroups(MavenSourcesImpl_TYPE_GEN_SOURCES)));
        list.addAll(Arrays.asList(sources.getSourceGroups(FolderTreeElement_XML)));
        list.addAll(Arrays.asList(sources.getSourceGroups(JavaProjectConstants_SOURCES_TYPE_JAVA)));
        list.addAll(Arrays.asList(sources.getSourceGroups(JavaProjectConstants_SOURCES_TYPE_RESOURCES)));
        list.addAll(Arrays.asList(sources.getSourceGroups(JavaProjectConstants_SOURCES_HINT_MAIN)));
        list.addAll(Arrays.asList(sources.getSourceGroups(JavaProjectConstants_SOURCES_HINT_TEST)));

        //filter out duplicated sourceroots
        Map<String, SourceGroup> linkedHashMap = new LinkedHashMap<>();
        for (SourceGroup sourceGroup : list) {
            linkedHashMap.put(sourceGroup.getRootFolder().getPath(), sourceGroup);
        }
        return linkedHashMap.values();
    }
}
