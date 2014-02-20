import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import keepers.CartesianPointKeeper;
import keepers.ClosedShellKeeper;
import keepers.MaxMeasures;
import utils.CommonUtils;
import utils.StepFileReader;
import entities.AdvancedFace;
import entities.ClosedShell;
import entities.FaceBoundAbstract;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static List<String> trace = new ArrayList<String>();
	private static boolean showGUI = true;

	private static void mainProcedure(String filePath) {
		print("-----start ");
		int firstDigit = -1, secondDigit = -1, thirdDigit = -1, fourthDigit = -1;
		StepFileReader sfr = new StepFileReader(filePath == null ? CommonUtils._PATH + "holes4-chamfers.STEP" : filePath);
		ClosedShell cs = new ClosedShell(sfr.getClosedShellLineId());
		ClosedShellKeeper.set(cs);
		AdvancedFace bottom = cs.getBottomPlane();
		if (bottom != null) {
			// non rotational
			print("non rotational");
			MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				firstDigit = 6;
				print("flat");
				if (bottom.getFaceOuterBound().isRectangle()) {
					print("bottom: rectangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 0;
					}
				} else if (bottom.getFaceOuterBound().isRightAngledTriangle()) {
					print("bottom: rightAngledTriangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 1;
					}
				} else if (bottom.getFaceOuterBound().isAllAnglesTheSame()) {
					print("bottom: same angled");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 2;
					}
				} else if (bottom.getFaceOuterBound().isCircularAndOrtogonal()) {
					print("bottom: CircularAndOrtogonal");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 3;
					}
				} else if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
					secondDigit = 4;
				} else {
					secondDigit = 9;
				}
				if (0 <= secondDigit && secondDigit <= 9) {
					int k = cs.getYOrientedFacesCount(); 
					if (k == 2) {
						if (cs.getTopPlane().getFaceOuterBound().hasTopChamfers()) {
							fourthDigit = 1;
							print("machining: has chambers");
						} else {
							fourthDigit = 0;
							print("machining: no machining");
						}
					} else if (k == 3) {
						fourthDigit = 2;
						print("machining: stepped 2");
					} else if (k > 3) {
						fourthDigit = 3;
						print("machining: stepped > 2");
					}
				}
				if (0 <= secondDigit && secondDigit <= 9) {
					int innerBounds = bottom.getFaceInnerBound().size();
					if (innerBounds == 0) {
						thirdDigit = 0;
					} else if (innerBounds == 1) {
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							if (faceBound.areAdjacentsXZOriented()) {
								thirdDigit = 1;
								print("one principal bore");
							}
						}
					} else if (innerBounds == 2) {
						boolean res = true;
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							res &= faceBound.areAdjacentsXZOriented();
						}
						if (res) {
							thirdDigit = 4;
							print("two principal bores, parallel");							
						}
					} else if (innerBounds > 2) {
						boolean res = true;
						for (FaceBoundAbstract faceBound : bottom.getFaceInnerBound()) {
							res &= faceBound.areAdjacentsXZOriented();
						}
						if (res) {
							thirdDigit = 5;
							print("several principal bores, parallel");							
						} else {
							thirdDigit = 6;
							print("several principal bores, non parallel");
						}
					} else {
						thirdDigit = 9;
					}
				}
			} else if (m.maxLength / m.maxWidth > 3) {
				// long
				print("long");
			} else if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight < 4) {
				// cubic
				print("cubic");
			}
		}
		print("-----done: " + firstDigit + secondDigit + thirdDigit + fourthDigit + "x");
	}
	
	private static void print(String s) {
		System.out.println(s);
		trace.add(s);
	}
	
	public Main() {
		JPanel p = new JPanel();
		open.addActionListener(new OpenL());
		p.add(open);
		Container cp = getContentPane();
		cp.add(p, BorderLayout.SOUTH);
		filename.setEditable(false);
		p = new JPanel();
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.add(filename);
        p.add(Box.createVerticalStrut(5)); //extra space
		textArea = new JTextArea(8, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		p.add(scrollPane);
        p.add(Box.createVerticalStrut(5)); //extra space
		cp.add(p, BorderLayout.NORTH);
	}

	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			int rVal = c.showOpenDialog(Main.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String path = c.getSelectedFile().getAbsolutePath();
				filename.setText(path);
				mainProcedure(path);
				textArea.setText("");
				for (String s : trace) {
					textArea.append(s);
					textArea.append("\n");
				}
				trace.clear();
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
			}
		}
	}

	public static void main(String[] arr) {
		if (showGUI) {
			run(new Main(), 450, 250);
		} else {
			mainProcedure(null);
		}
	}

	public static void run(JFrame frame, int width, int height) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setTitle("ISO 10303 STEP Tracer");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}

	private JTextField filename = new JTextField();
	private JTextArea textArea = new JTextArea(); 
	private JButton open = new JButton("Open STEP File");

}
