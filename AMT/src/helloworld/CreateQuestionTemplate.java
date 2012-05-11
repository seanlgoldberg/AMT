package helloworld;

import java.util.Vector;
import java.io.*;

public class CreateQuestionTemplate {
	
	

	public CreateQuestionTemplate(){
		
	}
	
	public void generateQuestion(Vector<String[]> allTokens, int entPosition) {
	//public void generateQuestion() {	
		
			try{
				
				FileWriter fstream = new FileWriter("Q1.xml");
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				out.write("  <QuestionForm xmlns=\"http://mechanicalturk.amazonaws.com/AWSMechanicalTurkDataSchemas/2005-10-01/QuestionForm.xsd\n\">\n");
				out.write("    <Overview>\n");
				out.write("      <Title>Bibliographic Labeling</Title>\n");
				out.write("      <Text>In referencing published academic papers, a shortened citation containing the pertinent information is normally used.  The fields that are traditionally given are:</Text>\n");
				out.write("      <List>\n");
				out.write("        <ListItem>Title - The title of the paper.</ListItem>\n");
				out.write("        <ListItem>Author - The author of the paper.</ListItem>\n\n");
				out.write("        <ListItem>Conference - Organization where the paper was presented.  Usually, but not always, listed as an acronym.</ListItem>\n");
				out.write("        <ListItem>Proceedings - Title of the published conference document.  Usually contains the conference acronym and date of the conference.</ListItem>\n");
				out.write("        <ListItem>Series - Higher level general topic of which the proceedings is a part.</ListItem>\n");
				out.write("		   <ListItem>Publisher - Company publishing the proceedings.</ListItem>\n");
				out.write("		   <ListItem>ISBN - String of 10 or 13 digits uniquely identifying the book the paper was published in.</ListItem>\n");
				out.write("        <ListItem>Year - Year the paper was published.</ListItem>\n");
				out.write("		 </List>\n");
				out.write("		 <Text>A tagged example is given below.</Text>\n");
				out.write("      <Text>Benefits of Case-Based Reasoning in Color Matching. |Title</Text>\n");
				out.write("      <Text>William Cheetham |Author</Text>\n");
				out.write("      <Text>ICCBR |Conference</Text>\n");
				out.write("      <Text>3-540-42358-3 |ISBN</Text>\n");
				out.write("      <Text>Springer |Publisher</Text>\n");
				out.write("      <Text>Lecture Notes in Computer Science |Series</Text>\n");
				out.write("      <Text>Case-Based Reasoning Research and Development, 4th International Conference on Case-Based Reasoning, ICCBR 2001, Vancouver, BC, Canada, July 30 - August 2, 2001, Proceedings |Proceedings</Text>\n");
				out.write("      <Text>2001 |Year</Text>\n");
				out.write("      <FormattedContent>\n");
				out.write("        <![CDATA[\n");
				out.write("        <br></br>\n");
				out.write("        <p>Please look at the bibliographic sequences below and select the appropriate label for the <b>bolded</b> token:</p>]]>\n");
				out.write("      </FormattedContent>\n");
				out.write("    </Overview>\n");
				
				for (int i=0; i<allTokens.size(); i++) {
				
					String[] tokens = allTokens.elementAt(i);
					
					out.write("    <Question>\n");
					out.write("    <QuestionIdentifier>1</QuestionIdentifier>\n");
					out.write("    <QuestionContent>\n");
					out.write("        <FormattedContent>\n");
					out.write("          <![CDATA[\n");
					out.write("          <br></br>\n");
					out.write("          <p><font size=\"2\">");
						for (int j=0; j<tokens.length; j++) {
							if (j==entPosition) {
								out.write("<b>" + tokens[j] + "</b> ");
							}
							else {
								out.write(tokens[j] + " ");				
							}
						}
					out.write("</p>\n");
					out.write("          <br></br>]]>\n");
					out.write("        </FormattedContent>\n");
					out.write("      </QuestionContent>\n");
					out.write("      <AnswerSpecification>\n");
					out.write("        <SelectionAnswer>\n");
					out.write("          <StyleSuggestion>radiobutton</StyleSuggestion>\n");
					out.write("          <Selections>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>author</SelectionIdentifier>\n");
					out.write("              <Text>Author</Text>\n");
					out.write("            </Selection>\n");       
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>title</SelectionIdentifier>\n");
					out.write("              <Text>Title</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>conference</SelectionIdentifier>\n");
					out.write("              <Text>Conference</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>proceedings</SelectionIdentifier>\n");
					out.write("              <Text>Proceedings</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>series</SelectionIdentifier>\n");
					out.write("              <Text>Series</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>publisher</SelectionIdentifier>\n");
					out.write("              <Text>Publisher</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>isbn</SelectionIdentifier>\n");
					out.write("              <Text>ISBN</Text>\n");
					out.write("            </Selection>\n");
					out.write("            <Selection>\n");
					out.write("              <SelectionIdentifier>year</SelectionIdentifier>\n");
					out.write("              <Text>Year</Text>\n");
					out.write("            </Selection>\n");        
					out.write("          </Selections>\n");
					out.write("        </SelectionAnswer>\n");
					out.write("      </AnswerSpecification>\n");
					out.write("    </Question>\n");
										
						
				}
				
				out.write("  </QuestionForm>\n");

				
				out.close();
				
				
				
			}catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		//}
	}
}
