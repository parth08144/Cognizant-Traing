public class FactoryMethodPatternExample {
    interface Document {
        void open();
    }

    class WordDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening Word Document");
        }

    
    }

    class PdfDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening pdf Document");
        }
    }

    class ExcelDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening Excel Document");
        }
    }

    // Abstract class
    abstract class DocumentFactory {
        abstract Document createDocument();
    }

    // Concrete classes
    class WordDocumentFactory extends DocumentFactory {
        @Override
        Document createDocument() {
            return new WordDocument();
        }
    }
    class PdfDocumentFactory extends DocumentFactory {
        @Override
        Document createDocument() {
            return new PdfDocument();
        }
    }
    class ExcelDocumentFactory extends DocumentFactory {
        @Override
        Document createDocument() {
            return new ExcelDocument();
        }
    }

    //Test

    public static void main(String[] args) {
        FactoryMethodPatternExample example = new FactoryMethodPatternExample();

        DocumentFactory wordFactory = example.new WordDocumentFactory();
        Document wordDocument = wordFactory.createDocument();
        wordDocument.open();

        DocumentFactory pdfFactory = example.new PdfDocumentFactory();
        Document pdfDocument = pdfFactory.createDocument();
        pdfDocument.open();

        DocumentFactory excelFactory = example.new ExcelDocumentFactory();
        Document excelDocument = excelFactory.createDocument();
        excelDocument.open();
    }
    
}
