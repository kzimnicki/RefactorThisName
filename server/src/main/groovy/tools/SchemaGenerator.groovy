package tools

import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.tool.hbm2ddl.SchemaExport

public class SchemaGenerator {
    private AnnotationConfiguration cfg;

    public SchemaGenerator(String packageName) throws Exception {
        cfg = new AnnotationConfiguration();
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        for (Class<Object> clazz: getClasses(packageName)) {
            cfg.addAnnotatedClass(clazz);
        }
    }

    private void generate(String dialect) {
        cfg.setProperty("hibernate.dialect", dialect);
        SchemaExport export = new SchemaExport(cfg);
        export.setDelimiter(";");
        export.setOutputFile("server/sql/ddl_explain_cc_database.sql");
        export.execute(true, false, false, false);
    }

    private List<Class> getClasses(String packageName) throws Exception {
        List<Class> classes = new ArrayList<Class>();
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        File directory = new File(resource.getFile());
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            if (files[i].endsWith(".class")) {
                classes.add(Class.forName(packageName + '.'
                        + files[i].substring(0, files[i].length() - 6)));
            }
        }
        return classes;
    }

    public static void main(String[] args) throws Exception {
        SchemaGenerator gen = new SchemaGenerator("cc.explain.server.model");
        gen.generate("org.hibernate.dialect.MySQLDialect");
    }
}