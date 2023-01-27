package model.pais.DAO.impl;

import model.estado.Estado;
import model.pais.DAO.PaisDAO;
import model.pais.Pais;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaisXMLDAO implements PaisDAO {

    private String diretorioXML = "database/xml/paises";

    @Override
    public void cadastrar(Pais pais) {




        try {
            DocumentBuilderFactory builderFac = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = builderFac.newDocumentBuilder();

            Document document = builder.newDocument();
            Element elementPais = document.createElement("pais");
            document.appendChild(elementPais);

            Element elementID = document.createElement("id");
            elementID.setTextContent(pais.getId().toString());
            elementPais.appendChild(elementID);

            Element elementNome = document.createElement("nome");
            elementNome.setTextContent(pais.getNome());
            elementPais.appendChild(elementNome);

            Element elementSigla = document.createElement("sigla");
            elementSigla.setTextContent(pais.getSigla().toString());;
            elementPais.appendChild(elementSigla);



            File file = new File(diretorioXML, pais.getId().toString() + ".xml");
            try (FileOutputStream fileOS = new FileOutputStream(file)){

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(fileOS);

                transformer.transform(source, result);

            } catch (IOException | TransformerException ex){
                throw new RuntimeException(ex);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Pais> listar() {



        FilenameFilter filter = (dir, name) -> name.endsWith(".xml");
        File diretorioRaiz = new File(diretorioXML);
        List<Pais> paises = new ArrayList<>();
        for (File arquivo : diretorioRaiz.listFiles(filter)){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(arquivo);

                Element elementPais = document.getDocumentElement();
                Node elementId = elementPais.getElementsByTagName("id").item(0);
                Node elementNome = elementPais.getElementsByTagName("nome").item(0);
                Node elementSigla = elementPais.getElementsByTagName("sigla").item(0);


                Pais pais = new Pais();
                pais.setId(UUID.fromString(elementId.getTextContent()));
                pais.setNome(elementNome.getTextContent());
                pais.setSigla(elementSigla.getTextContent());

                paises.add(pais);


            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return paises;
    }

    @Override
    public Pais buscar(UUID id) {


        File arquivo = new File(diretorioXML, id.toString()+".xml");

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(arquivo);
            Element elementPais = document.getDocumentElement();
            Node elementId = elementPais.getElementsByTagName("id").item(0);
            Node elementNome = elementPais.getElementsByTagName("nome").item(0);
            Node elementSigla = elementPais.getElementsByTagName("sigla").item(0);

            Pais pais = new Pais();
            pais.setId(UUID.fromString(elementId.getTextContent()));
            pais.setNome(elementNome.getTextContent());
            pais.setSigla(elementSigla.getTextContent());

            return pais;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void atualizar(UUID id, Pais pais) {


        File arquivo = new File(diretorioXML, id.toString() + ".xml");
        arquivo.delete();

        pais.setId(id);

        cadastrar(pais);


    }

    @Override
    public Pais apagar(UUID id) {



        Pais pais = buscar(id);

        if (pais!=null) {
            File arquivo = new File(diretorioXML, id.toString() + ".xml");
            arquivo.delete();
        }

        return pais;
    }
}
