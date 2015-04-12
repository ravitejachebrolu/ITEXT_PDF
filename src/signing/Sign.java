package signing;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;



	public class Sign {

		/**
		 * Creates a signed PDF file.
		 * 
		 * @param args
		 *            no arguments needed here
		 * @throws IOException 
		 * @throws DocumentException 
		 * @throws KeyStoreException 
		 * @throws CertificateException 
		 * @throws NoSuchAlgorithmException 
		 * @throws UnrecoverableKeyException 
		 */
		public static void main(String[] args) throws DocumentException, IOException, 
		KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
			

			createPdf();

			PdfReader reader;
			
				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				ks.load(new FileInputStream("C:/Program Files/Java/jre1.8.0_31/bin/clientkeystore"), "shafiulla"
						.toCharArray());
				PrivateKey key = (PrivateKey) ks.getKey("client", "ShafiUlla"
						.toCharArray());
				Certificate[] chain = ks.getCertificateChain("client");
				reader = new PdfReader("D:/HelloWorldStamped.pdf");
				int n = reader.getNumberOfPages();
				FileOutputStream os = new FileOutputStream("D:/signed_message.pdf");
				
				PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
				PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
				appearance.setCrypto(key, chain, null, PdfSignatureAppearance.SELF_SIGNED);
				//appearance.setReason("It's personal.");
				//appearance.setLocation("Foobar");
				for(int i=1;i<=n;i++){
				appearance.setVisibleSignature(new Rectangle(20, 15, 586, 50), i,"mysignature");//
				}
				
				stamper.close();
			
			
		}
			
		public static void createPdf() {
			Document document = new Document();
			try {
				// step 2:
				// we create a writer
				PdfWriter writer = PdfWriter.getInstance(
				// that listens to the document
						document,
						// and directs a PDF-stream to a file
						new FileOutputStream("D:/HelloWorldStamped.pdf"));
				// step 3: we open the document
				
	            BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
	          

	            // headers and footers must be added before the document is opened
	            HeaderFooter footer = new HeaderFooter(
	                        new Phrase("  ", new Font(bf_times)), true);
	            footer.setBorder(Rectangle.NO_BORDER);
	            footer.setAlignment(Element.ALIGN_RIGHT);
	            document.setFooter(footer);

	            HeaderFooter header = new HeaderFooter(
	                        new Phrase("festival", new Font(bf_times)), false);
	            header.setAlignment(Element.ALIGN_CENTER);
	            document.setHeader(header);
				document.open();
				int y_line1 = 1000;
	            int y_line2 = y_line1 - 50;
	            int y_line3 = y_line2 - 50;

	            // draw a few lines ...
	            Paragraph par = new Paragraph("bold paragraph");
	            par.getFont().setStyle(Font.BOLD);
	            document.add(par);


	            // start second page
	            writer.setPageEmpty(false);
	            document.newPage();
	            Paragraph par1 = new Paragraph("bold paragraph");
	            par1.getFont().setStyle(Font.BOLD);
	            document.add(par);

	            writer.setPageEmpty(false);
	            document.newPage();

	            // demonstrate some table features
	            

				// step 4: we add a paragraph to the document
				document
						.add(new Paragraph("This is a personal message from Laura."));
			} catch (DocumentException de) {
				System.err.println(de.getMessage());
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
			}

			// step 5: we close the document
			document.close();
		}
	}

