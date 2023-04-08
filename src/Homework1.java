import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;
//import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class Homework1 {

    //Task01 and Task02
    public static class T1 {
        void doStuff() {

            String personURI = "https://www.linkedin.com/in/verica-stoilkova-482722255/";
            String fullName = "Verica Stoilkova";
            String birthDate = "10.09.2000";
            String givenName = "Verica";
            String familyName = "Stoilkova";
            String country = "Macedonia";
            String region = "Europe";
            String gender = "female";


            //create an empty Model
            Model model = ModelFactory.createDefaultModel();

            //create the resource
            // Resource johnSmith= (Resource) model.createResource(personURI);//

            Resource myData = model.createResource(personURI)
                    .addProperty(VCARD.FN, fullName)
                    .addProperty(VCARD.BDAY, birthDate)
                    .addProperty(FOAF.gender, gender)
                    .addProperty(VCARD.Country, country)
                    .addProperty(VCARD.Region, region)
                    .addProperty(VCARD.EMAIL, "verica100ilkova@hotmail.com")
                    .addProperty(VCARD.N, model.createResource()
                            .addProperty(VCARD.Given, givenName)
                            .addProperty(VCARD.Family, familyName));


            StmtIterator itr = model.listStatements();

            System.out.println("\n\n\n\n=======================");
            System.out.println("Printing with model.listStatements();");
            while (itr.hasNext())
                System.out.println(((StmtIterator) itr).nextStatement());
            System.out.println("=====================================\n\n\n\n");


            System.out.println("\n\n\n\n=====================================");
            System.out.println("Printing with model.write() in RDF/XML");
            model.write(System.out, "RDF/XML");
            System.out.println("=====================================\n\n\n\n");


            System.out.println("\n\n\n\n=====================================");
            System.out.println("Printing with RDFDataMgr.write() in Pretty RDF/XML");
            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PRETTY);
            System.out.println("=====================================\n\n\n\n");


            System.out.println("\n\n\n\n=====================================");
            System.out.println("Printing with model.write() in N-TRIPLES");
            model.write(System.out, "NT");
            System.out.println("=====================================\n\n\n\n");


            System.out.println("\n\n\n\n=====================================");
            System.out.println("Printing with model.write() in TURTLE");
            model.write(System.out, "TTL");
            System.out.println("=====================================\n\n\n\n");

        }
    }

    public static class T3 {
        void doStuff() throws FileNotFoundException {
            Model model = ModelFactory.createDefaultModel();
            InputStream is = new FileInputStream("C:\\Users\\hp\\Desktop\\t3.xml");
            model.read(is, "RDFXML");
            StmtIterator it = model.listStatements();
            while (it.hasNext())
                System.out.println(it.nextStatement());

        }

    }

    public static class T4 {
        void doStuff() throws FileNotFoundException {
            Model model = ModelFactory.createDefaultModel();
            InputStream is = new FileInputStream("C:\\Users\\hp\\Desktop\\t3.xml");
            model.read(is, "RDFXML");
            String personURI = "https://www.linkedin.com/in/verica-stoilkova-482722255/";
            Resource resource = model.getResource(personURI);
            System.out.println(resource.getProperty(VCARD.FN));
            System.out.println(resource.getProperty(VCARD.BDAY));
            System.out.println(resource.getProperty(VCARD.EMAIL));


        }
    }

    public static class T5 {
        void task5_1() throws FileNotFoundException {
            Model model = ModelFactory.createDefaultModel();
            InputStream str = new FileInputStream("C:\\Users\\hp\\Desktop\\d.ttl");
            model.read(str, "TTL");

            ResIterator iterator = model.listResourcesWithProperty(RDFS.label);

            List<String> med = new ArrayList<>();

            while (iterator.hasNext())
                med.add(iterator.nextResource().getProperty(RDFS.label).getLiteral().getString());

            ArrayList<String> medSorted = (ArrayList<String>) med.stream().distinct().collect(Collectors.toList());
            Collections.sort(medSorted);

            System.out.println("\n\n\n\n=====================================\n\n\n\n");
            System.out.println("Printing medicines sorted and distinct");
            System.out.println("\n\n\n\n=====================================");
            medSorted.forEach(System.out::println);
            System.out.println("=====================================\n\n\n\n");

        }

        void task5_2() throws FileNotFoundException {
            Model model = ModelFactory.createDefaultModel();
            InputStream str = new FileInputStream("C:\\Users\\verica\\Desktop\\d.ttl");
            model.read(str, "TTL");

            StmtIterator iterator = model.listStatements();

            System.out.println("\n\n\n\n=====================================\n\n\n\n");
            System.out.println("Printing paracetamol - relations and values");
            System.out.println("\n\n\n\n=====================================");

            while (iterator.hasNext()) {
                Statement statement = iterator.nextStatement();
                if (statement.getObject().isLiteral() && statement.getLiteral().getString().equals("Paracetamol"))
                    System.out.println(statement);
            }


        }
    }

    void task5_24() throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();
        InputStream is = new FileInputStream("C:\\Users\\verica\\Desktop\\hifm-dataset.ttl");
        model.read(is, RDFFormat.TURTLE.toString(), "TURTLE");
        Property similarTo = new PropertyImpl("http://purl.org/net/hifm/ontology#similarTo");
        AtomicReference<StmtIterator> iterator = new AtomicReference<>(model.listStatements());
        System.out.println("\n\n\n\n=====================================\n\n\n\n");
        System.out.println("TASK 5.23 Printing SALBUTAMOL - similarTo");
        System.out.println("\n\n\n\n=====================================");
        List<Resource> salbutamol = new ArrayList<>();
        while (iterator.get().hasNext()) {
            Statement statement = iterator.get().nextStatement();
            if (statement.getObject().isLiteral() && statement.getLiteral().getString().contains("Salbutamol"))
                salbutamol.add(statement.getSubject());
        }
        ArrayList<Resource> salbutamolDistinct = (ArrayList<Resource>) salbutamol.stream().distinct().collect(Collectors.toList());
        salbutamolDistinct.forEach(x -> {
            List<String> similarMeds = new ArrayList<>();
            String xName = x.getProperty(RDFS.label).getString();
            System.out.println("\n\n=====================================\nPRINTING FOR: " + xName + " URI:" + x.toString());
            iterator.set(model.listStatements());
            while (iterator.get().hasNext()) {
                Statement statement = iterator.get().nextStatement();
                //System.out.println(statement);
                Statement med = statement.getSubject().getProperty(similarTo);
                if (med != null) {
                    if (med.getSubject().equals(x)) {
                        String newLine = "similarTo "
                                + med.getObject().asResource().getProperty(RDFS.label).getString()
                                + " URI: " + med.getObject();
                        if (!similarMeds.contains(newLine))
                            similarMeds.add(newLine);
                    }
                }
            }
            similarMeds.forEach(System.out::println);
        });
        System.out.println("=====================================\n\n\n\n");
    }

    void task5_25() throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();

        InputStream is = new FileInputStream("\"C:\\Users\\hp\\Desktop\\hifm-dataset.ttl");
        model.read(is, RDFFormat.TURTLE.toString(), "TURTLE");

        Property refPrice = new PropertyImpl("http://purl.org/net/hifm/ontology#refPriceWithVAT");
        StmtIterator iterator = model.listStatements();
        ResIterator priceIterator = model.listResourcesWithProperty(refPrice);

        List<Resource> salbutamol = new ArrayList<>();

        while (iterator.hasNext()) {
            Statement statement = iterator.nextStatement();
            if (statement.getObject().isLiteral() && statement.getLiteral().getString().contains("Salbutamol"))
                salbutamol.add(statement.getSubject());
        }

        ArrayList<Resource> salbutamolDistinct = (ArrayList<Resource>) salbutamol.stream().distinct().collect(Collectors.toList());
        iterator = model.listStatements();

        for (Resource resource : salbutamolDistinct) {
            priceIterator = model.listResourcesWithProperty(refPrice);
            while (priceIterator.hasNext()) {
                Resource temp = priceIterator.nextResource();
                String medName = resource.getProperty(RDFS.label).getString();
                //System.out.println(medName);
                if (temp.getURI().equals(resource.toString()))
                    System.out.println(medName + " : " + temp.getProperty(refPrice).getString() + " den.");
            }
        }


    }



    public static void main(String[] args) throws FileNotFoundException {

//For you to test out the tasks, just uncomment the specific task you wish to test
        //T1 task1 = new T1();
        //task1.doStuff();

        T3 task3 = new T3();
        task3.doStuff();

        //T4 task4 = new T4();
        //task4.doStuff();

        //Task5 task5 = new Task5();
        //task5.task5_22();
        //task5.task5_23();
        //task5.task5_24();
        //task5.task5_25();
    }
}
