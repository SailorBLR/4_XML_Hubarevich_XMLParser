package by.hubarevich.xml.main;

import by.hubarevich.xml.action.SimpleVoucherHandler;
import by.hubarevich.xml.action.VouchersErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by Anton on 28.12.2015.
 */
public class ValidatorSAX {
    public static void main(String[ ] args) {
        String filename = "resources/travel-vouchers.xml";
        String schemaname = "resources/travel-vouchers.xsd";
        String logname = "logs/log.txt";
        Schema schema = null;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        try {
// установка проверки с использованием XSD
            schema = factory.newSchema(new File(schemaname));
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setSchema(schema);
// создание объекта-парсера
            SAXParser parser = spf.newSAXParser();
// установка обработчика ошибок и запуск
            parser.parse(filename, new VouchersErrorHandler(logname));
            System.out.println(filename + " is valid");


            // создание SAX-анализатора
            XMLReader reader = XMLReaderFactory.createXMLReader();
            SimpleVoucherHandler handler = new SimpleVoucherHandler();
            reader.setContentHandler(handler);
            reader.parse("resources/travel-vouchers.xml");

        } catch (ParserConfigurationException e) {
            System.err.println(filename + " config error: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println(filename + " SAX error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }

    }
}
