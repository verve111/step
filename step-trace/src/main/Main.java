package main;
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
import entities.CartesianPoint;
import entities.ClosedShell;
import entities.CylindricalSurface;
import entities.FaceOuterBound;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static List<String> trace = new ArrayList<String>();
	private static boolean showGUI = false;
	private static ClosedShell cs;
	private static boolean isTest;
	
	public static String mainProcedure(String filePath, boolean isTest) {
		Main.isTest = isTest;
		print("-----start ");
		int firstDigit = -1, secondDigit = -1, thirdDigit = -1, fourthDigit = -1;
		StepFileReader sfr = new StepFileReader(filePath == null ? (CommonUtils._PATH_PRODUCTION + "32-351-7/32-351-7.STEP") : filePath);
		cs = new ClosedShell(sfr.getClosedShellLineId());
		ClosedShellKeeper.set(cs);
		MaxMeasures m = CartesianPointKeeper.getMaxShapeMeasures();
		AdvancedFace bottom = cs.getBottomPlane();
		AdvancedFace front = cs.getFrontPlane();
		AdvancedFace back = cs.getBackPlane();
		int cylinders = cs.getCylindricalSurfacesWithoutThroughHoles().size();
		if (cylinders > 0
				&& (bottom == null || (bottom != null && isBottomLessThanCircleOfCylinder(cs.getCylindricalSurfacesWithoutThroughHoles(), bottom)))) {
			// rotational
			print("rotational shape");
			double rat = m.maxLength / m.maxWidth;
			firstDigit = rat <= 0.5 ? 0 : (rat >= 0.5 && rat < 3) ? 1 : rat >= 3 ? 2 : -1;
			if (cylinders == 1) {
				print("one external cylinder");
			} else if (cylinders == 2) {
				print("two external cylinders");					
			} else if (cylinders == 3) {
				print("three external cylinders");					
			} else {
				print("more than 3 external cylinders");
			}
			secondDigit = getExternMachinigRotational(cylinders);
			thirdDigit = getThirdDigitRotational(cylinders);
			fourthDigit = 0;
		} else if (bottom != null) {
			// non rotational
			print("non rotational");
			if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight >= 4) {
				// flat
				firstDigit = 6;
				print("flat");
				if (bottom.getFaceOuterBound().isRectangle()) {
					print("bottom: rectangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 0;
					}
				} else if (bottom.getFaceOuterBound().isTriangle()) {
					print("bottom: triangle");
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
					fourthDigit = getFourthDigit();
					thirdDigit = getThirdDigit(bottom);
				}
			} else if (m.maxLength / m.maxWidth <= 3 && m.maxLength / m.maxHeight < 4) {
				// cubic
				print("cubic");
				firstDigit = 8;
				if (bottom.getFaceOuterBound().isRectangle()) {
					print("bottom: rectangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 0;
					}
				} else if (bottom.getFaceOuterBound().isTriangle()) {
					print("bottom: triangle");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 1;
					}					
				} else if (bottom.getFaceOuterBound().isRecangPrismatic()) {
					print("bottom: rectangular prisms");
					if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
						secondDigit = 2;
					}					
				} else if (bottom.getFaceOuterBound().areAdjacentsXZOriented()) {
					print("bottom: other shape");
					secondDigit = 5;
				}
				if (0 <= secondDigit && secondDigit <= 9) {
					fourthDigit = getFourthDigit();
					thirdDigit = getThirdDigit(bottom);
				}
			} else if (m.maxLength / m.maxWidth > 3) {
				// long 
				print("long");
				firstDigit = 7;
				thirdDigit = 0;
				fourthDigit = 0;
				if (front != null && back != null) {
					FaceOuterBound frontFob = front.getFaceOuterBound();
					FaceOuterBound backFob = back.getFaceOuterBound();
					if (!frontFob.isCircle() && !backFob.isCircle()) {
						if (CommonUtils.arePlanesEqualAlongZ(frontFob, backFob)) {
							if (frontFob.isRectangle() && backFob.isRectangle() && bottom.getFaceOuterBound().isRectangle()) {
								print("front plane, back plane: uniform (equal) cross section (rectangular)");
								secondDigit = 0;
								fourthDigit = getFourthDigit();
								thirdDigit = getThirdDigit(bottom);
							} else if (frontFob.isTriangle() && backFob.isTriangle() && frontFob.areAdjacentsXYOriented()
									&& backFob.areAdjacentsXYOriented()) {
								print("front plane, back plane: uniform (equal) cross section (triangular)");
								secondDigit = 1;
							} else if (frontFob.areAdjacentsXYOriented() && backFob.areAdjacentsXYOriented()) {
								print("front plane, back plane: uniform (equal) cross section");
								secondDigit = 2;							
							}
						} else {
							if (frontFob.isRectangle() && backFob.isRectangle()) {
								print("front plane, back plane: varying cross section (rectangular)");
								secondDigit = 3;
							} else if (frontFob.isTriangle() && backFob.isTriangle()) {
								print("front plane, back plane: varying cross section (triangular)");
								secondDigit = 4;
							} else {
								print("front plane, back plane: varying cross sections");
								secondDigit = 5;
							}
						}
						if (0 <= secondDigit && secondDigit <= 9) {
							fourthDigit = getFourthDigit();
							thirdDigit = getThirdDigit(bottom);
						}
					}
				}
			}
		}
		String res = "" + firstDigit + secondDigit + thirdDigit + fourthDigit + "0";
		print("-----done: " + res);
		return res;
	}
	
	private static boolean isBottomLessThanCircleOfCylinder(List<AdvancedFace> cylinders, AdvancedFace bottom) {
		float biggestRadius = 0;
		for (AdvancedFace af : cylinders) {
			if (af.getSurfGeometry() instanceof CylindricalSurface && af.getSurfGeometry().getDirection().isYOriented()) {
				float r = ((CylindricalSurface)af.getSurfGeometry()).getRadius();
				if (r > biggestRadius) {
					biggestRadius = r;
				}
			}
		}
		float maxWidth, maxLength, minX = 0, maxX = 0, minZ = 0, maxZ = 0;
		int i = 0;
		for (CartesianPoint p : bottom.getFaceOuterBound().getAllPoints()) {
			if (i++ == 0) {
				minX = p.getX();
				maxX = p.getX();
				minZ = p.getZ(); 
				maxZ = p.getZ();
			}
			if (p.getX() < minX) {
				minX = p.getX();
			} else if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getZ() < minZ) {
				minZ = p.getZ();
			} else if (p.getZ() > maxZ) {
				maxZ = p.getZ();
			}
		}
		maxWidth = maxZ - minZ;
		maxLength = maxZ - minZ;
		biggestRadius *= 2;
		return maxWidth < biggestRadius && maxLength < biggestRadius;
	}
	
	private static int getThirdDigitRotational(int cylinderCount) {
		if (cylinderCount == 1 || cylinderCount == 2) {
			if (cs.getThroughHolesCount() != 0) {
				print("inner bore is found");
				return 1;
			} 
		} else if (cylinderCount == 3) {
			if (cs.getThroughHolesCount() != 0) {
				print("inner bore is found");
				return 4;
			}  
		}
		print("no inner shape is found");
		return 0;
	}
	
	private static int getExternMachinigRotational(int cylinderCount) {
		boolean isGroove = false;
		for (AdvancedFace af : cs.getCylindricalSurfacesWithoutThroughHoles()) {
			if (af.getFaceInnerBound().size() > 0) {
				print("external groove is found");
				isGroove = true;
				break;
			}
		}
		if (isGroove) {
			if (cylinderCount == 1 || cylinderCount == 2) {
				return 3;
			} else if (cylinderCount == 3) {
				return 6;
			} 
		} else {
			if (cylinderCount == 2) {
				print("no external machining");
				return 1;
			} else if (cylinderCount == 3) {
				print("no external machining");
				return 4;
			}
		}
		print("no external machining");
		return 0;
	}
	

	
	private static int getFourthDigit() {
		int fourthDigit = -1;
		int k = cs.getYOrientedPlaneFacesCount();
		if (cs.hasUpperMachining()) {
			fourthDigit = 7;
			print("machining: curved surface");						
		} else if (k == 2) {
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
		return fourthDigit;
	}
	
	private static int getThirdDigit(AdvancedFace bottom) {
		int thirdDigit = -1;
		int innerHoles = cs.getThroughHolesCount();
		if (innerHoles == 0) {
			thirdDigit = 0;
		} else if (innerHoles == 1) {
			thirdDigit = 1;
			print("one principal bore");
		} else if (innerHoles == 2) {
			thirdDigit = 4;
			print("two principal bores, parallel");
		} else if (innerHoles > 2) {
			thirdDigit = 5;
			print("several principal bores, parallel");	
		}
		return thirdDigit;
	}
 	
	private static void print(String s) {
		if (isTest) {
			return;
		}
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
				mainProcedure(path, false);
				textArea.setText("");
				for (String s : trace) {
					textArea.append(s);
					textArea.append("\n");
				}
				trace.clear();
				CommonUtils.clearMaps();
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
			mainProcedure(null, false);
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
