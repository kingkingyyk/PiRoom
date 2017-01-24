package PiRoom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainUI extends JFrame {
	private static final long serialVersionUID = 6180122871006649713L;
	private JPanel contentPane;
	private JSlider sliderFanCtrlMotion;
	private JSlider sliderFanCtrlTemp;
	private JLabel lblFanCtrlMotionValue;
	private JLabel lblFanCtrlTempValue;
	private JSlider sliderLightCtrlMotion;
	private JSlider sliderLightCtrlLight;
	private JLabel lblLightCtrlMotionValue;
	private JLabel lblLightCtrlLightValue;
	private JLabel lblMotionReading;
	private JLabel lblLightReading;
	private JLabel lblTemperatureReading;
	private JToggleButton tglbtnFanCtrl;
	private JToggleButton tglbtnLightCtrl;
	private JToggleButton tglbtnCtrl;
	private JLabel lblFanCtrlMotion;
	private JLabel lblFanCtrlTemp;
	private JLabel lblLightCtrlMotion;
	private JLabel lblLightCtrlLight;
	private JLabel lblCtrlTime;

	public MainUI() {
		setResizable(false);
		setTitle("Pi Room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Control Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 257, 75);
		contentPane.add(panel);
		panel.setLayout(null);
		
		tglbtnCtrl = new JToggleButton("Manual");
		tglbtnCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PiRoom.setAutomationStatus(tglbtnCtrl.isSelected());
			}
		});
		tglbtnCtrl.setBounds(10, 21, 192, 43);
		panel.add(tglbtnCtrl);
		
		lblCtrlTime = new JLabel("N/A");
		lblCtrlTime.setBounds(208, 35, 39, 14);
		panel.add(lblCtrlTime);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Fan Control", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 90, 514, 108);
		contentPane.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		lblFanCtrlMotion = new JLabel("Motion :");
		GridBagConstraints gbc_lblFanCtrlMotion = new GridBagConstraints();
		gbc_lblFanCtrlMotion.anchor = GridBagConstraints.EAST;
		gbc_lblFanCtrlMotion.insets = new Insets(0, 0, 5, 5);
		gbc_lblFanCtrlMotion.gridx = 0;
		gbc_lblFanCtrlMotion.gridy = 0;
		panel_1.add(lblFanCtrlMotion, gbc_lblFanCtrlMotion);
		
		sliderFanCtrlMotion = new JSlider();
		sliderFanCtrlMotion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double v=sliderFanCtrlMotion.getValue()/100.0;
				PiRoom.setFanAutomationMotionThreshold(v,false);
			}
		});
		sliderFanCtrlMotion.setValue(1);
		sliderFanCtrlMotion.setMaximum(300);
		GridBagConstraints gbc_sliderFanCtrlMotion = new GridBagConstraints();
		gbc_sliderFanCtrlMotion.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderFanCtrlMotion.insets = new Insets(0, 0, 5, 5);
		gbc_sliderFanCtrlMotion.gridx = 1;
		gbc_sliderFanCtrlMotion.gridy = 0;
		panel_1.add(sliderFanCtrlMotion, gbc_sliderFanCtrlMotion);
		
		lblFanCtrlMotionValue = new JLabel("0 move/s");
		lblFanCtrlMotionValue.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblFanCtrlMotionValue = new GridBagConstraints();
		gbc_lblFanCtrlMotionValue.anchor = GridBagConstraints.WEST;
		gbc_lblFanCtrlMotionValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblFanCtrlMotionValue.gridx = 2;
		gbc_lblFanCtrlMotionValue.gridy = 0;
		panel_1.add(lblFanCtrlMotionValue, gbc_lblFanCtrlMotionValue);
		
		lblFanCtrlTemp = new JLabel("Temperature :");
		GridBagConstraints gbc_lblFanCtrlTemp = new GridBagConstraints();
		gbc_lblFanCtrlTemp.anchor = GridBagConstraints.EAST;
		gbc_lblFanCtrlTemp.insets = new Insets(0, 0, 5, 5);
		gbc_lblFanCtrlTemp.gridx = 0;
		gbc_lblFanCtrlTemp.gridy = 1;
		panel_1.add(lblFanCtrlTemp, gbc_lblFanCtrlTemp);
		
		sliderFanCtrlTemp = new JSlider();
		sliderFanCtrlTemp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double v=sliderFanCtrlTemp.getValue()/100.0;
				PiRoom.setFanAutomationTemperatureThreshold(v,false);
			}
		});
		sliderFanCtrlTemp.setValue(3000);
		sliderFanCtrlTemp.setMaximum(10000);
		GridBagConstraints gbc_sliderFanCtrlTemp = new GridBagConstraints();
		gbc_sliderFanCtrlTemp.insets = new Insets(0, 0, 5, 5);
		gbc_sliderFanCtrlTemp.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderFanCtrlTemp.gridx = 1;
		gbc_sliderFanCtrlTemp.gridy = 1;
		panel_1.add(sliderFanCtrlTemp, gbc_sliderFanCtrlTemp);
		
		lblFanCtrlTempValue = new JLabel("0 \u00B0C");
		lblFanCtrlTempValue.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblFanCtrlTempValue = new GridBagConstraints();
		gbc_lblFanCtrlTempValue.anchor = GridBagConstraints.WEST;
		gbc_lblFanCtrlTempValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblFanCtrlTempValue.gridx = 2;
		gbc_lblFanCtrlTempValue.gridy = 1;
		panel_1.add(lblFanCtrlTempValue, gbc_lblFanCtrlTempValue);
		
		tglbtnFanCtrl = new JToggleButton("OFF");
		tglbtnFanCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PiRoom.setFanStatus(tglbtnFanCtrl.isSelected(),true,false);
			}
		});
		GridBagConstraints gbc_tglbtnFanCtrl = new GridBagConstraints();
		gbc_tglbtnFanCtrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnFanCtrl.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnFanCtrl.gridx = 0;
		gbc_tglbtnFanCtrl.gridy = 2;
		panel_1.add(tglbtnFanCtrl, gbc_tglbtnFanCtrl);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Status", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(277, 11, 247, 75);
		contentPane.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblMotion = new JLabel("Motion :");
		GridBagConstraints gbc_lblMotion = new GridBagConstraints();
		gbc_lblMotion.anchor = GridBagConstraints.EAST;
		gbc_lblMotion.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotion.gridx = 0;
		gbc_lblMotion.gridy = 0;
		panel_2.add(lblMotion, gbc_lblMotion);
		
		lblMotionReading = new JLabel("0 move/s");
		GridBagConstraints gbc_lblMotionReading = new GridBagConstraints();
		gbc_lblMotionReading.anchor = GridBagConstraints.WEST;
		gbc_lblMotionReading.insets = new Insets(0, 0, 5, 0);
		gbc_lblMotionReading.gridx = 1;
		gbc_lblMotionReading.gridy = 0;
		panel_2.add(lblMotionReading, gbc_lblMotionReading);
		
		JLabel lblLight = new JLabel("Light :");
		GridBagConstraints gbc_lblLight = new GridBagConstraints();
		gbc_lblLight.anchor = GridBagConstraints.EAST;
		gbc_lblLight.insets = new Insets(0, 0, 5, 5);
		gbc_lblLight.gridx = 0;
		gbc_lblLight.gridy = 1;
		panel_2.add(lblLight, gbc_lblLight);
		
		lblLightReading = new JLabel("0%");
		GridBagConstraints gbc_lblLightReading = new GridBagConstraints();
		gbc_lblLightReading.anchor = GridBagConstraints.WEST;
		gbc_lblLightReading.insets = new Insets(0, 0, 5, 0);
		gbc_lblLightReading.gridx = 1;
		gbc_lblLightReading.gridy = 1;
		panel_2.add(lblLightReading, gbc_lblLightReading);
		
		JLabel lblTemperature = new JLabel("Temperature :");
		GridBagConstraints gbc_lblTemperature = new GridBagConstraints();
		gbc_lblTemperature.insets = new Insets(0, 0, 0, 5);
		gbc_lblTemperature.anchor = GridBagConstraints.EAST;
		gbc_lblTemperature.gridx = 0;
		gbc_lblTemperature.gridy = 2;
		panel_2.add(lblTemperature, gbc_lblTemperature);
		
		lblTemperatureReading = new JLabel("0 °C");
		GridBagConstraints gbc_lblTemperatureReading = new GridBagConstraints();
		gbc_lblTemperatureReading.anchor = GridBagConstraints.WEST;
		gbc_lblTemperatureReading.gridx = 1;
		gbc_lblTemperatureReading.gridy = 2;
		panel_2.add(lblTemperatureReading, gbc_lblTemperatureReading);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Light Control", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 209, 514, 108);
		contentPane.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		lblLightCtrlMotion = new JLabel("Motion :");
		GridBagConstraints gbc_lblLightCtrlMotion = new GridBagConstraints();
		gbc_lblLightCtrlMotion.anchor = GridBagConstraints.EAST;
		gbc_lblLightCtrlMotion.insets = new Insets(0, 0, 5, 5);
		gbc_lblLightCtrlMotion.gridx = 0;
		gbc_lblLightCtrlMotion.gridy = 0;
		panel_3.add(lblLightCtrlMotion, gbc_lblLightCtrlMotion);
		
		sliderLightCtrlMotion = new JSlider();
		sliderLightCtrlMotion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double v=sliderLightCtrlMotion.getValue()/100.0;
				PiRoom.setLightAutomationMotionThreshold(v,false);
			}
		});
		sliderLightCtrlMotion.setValue(1);
		sliderLightCtrlMotion.setMaximum(300);
		GridBagConstraints gbc_sliderLightCtrlMotion = new GridBagConstraints();
		gbc_sliderLightCtrlMotion.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderLightCtrlMotion.insets = new Insets(0, 0, 5, 5);
		gbc_sliderLightCtrlMotion.gridx = 1;
		gbc_sliderLightCtrlMotion.gridy = 0;
		panel_3.add(sliderLightCtrlMotion, gbc_sliderLightCtrlMotion);
		
		lblLightCtrlMotionValue = new JLabel("0 move/s");
		lblLightCtrlMotionValue.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblLightCtrlMotionValue = new GridBagConstraints();
		gbc_lblLightCtrlMotionValue.anchor = GridBagConstraints.WEST;
		gbc_lblLightCtrlMotionValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblLightCtrlMotionValue.gridx = 2;
		gbc_lblLightCtrlMotionValue.gridy = 0;
		panel_3.add(lblLightCtrlMotionValue, gbc_lblLightCtrlMotionValue);
		
		lblLightCtrlLight = new JLabel("Light :");
		GridBagConstraints gbc_lblLightCtrlLight = new GridBagConstraints();
		gbc_lblLightCtrlLight.anchor = GridBagConstraints.EAST;
		gbc_lblLightCtrlLight.insets = new Insets(0, 0, 5, 5);
		gbc_lblLightCtrlLight.gridx = 0;
		gbc_lblLightCtrlLight.gridy = 1;
		panel_3.add(lblLightCtrlLight, gbc_lblLightCtrlLight);
		
		sliderLightCtrlLight = new JSlider();
		sliderLightCtrlLight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double v=sliderLightCtrlLight.getValue()/100.0;
				PiRoom.setLightAutomationLightThreshold(v,false);
			}
		});
		sliderLightCtrlLight.setValue(300);
		sliderLightCtrlLight.setMaximum(10000);
		GridBagConstraints gbc_sliderLightCtrlLight = new GridBagConstraints();
		gbc_sliderLightCtrlLight.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderLightCtrlLight.insets = new Insets(0, 0, 5, 5);
		gbc_sliderLightCtrlLight.gridx = 1;
		gbc_sliderLightCtrlLight.gridy = 1;
		panel_3.add(sliderLightCtrlLight, gbc_sliderLightCtrlLight);
		
		lblLightCtrlLightValue = new JLabel("0 %");
		lblLightCtrlLightValue.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblLightCtrlLightValue = new GridBagConstraints();
		gbc_lblLightCtrlLightValue.anchor = GridBagConstraints.WEST;
		gbc_lblLightCtrlLightValue.insets = new Insets(0, 0, 5, 0);
		gbc_lblLightCtrlLightValue.gridx = 2;
		gbc_lblLightCtrlLightValue.gridy = 1;
		panel_3.add(lblLightCtrlLightValue, gbc_lblLightCtrlLightValue);
		
		tglbtnLightCtrl = new JToggleButton("OFF");
		tglbtnLightCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PiRoom.setLightStatus(tglbtnLightCtrl.isSelected(),true,false);
			}
		});
		GridBagConstraints gbc_tglbtnLightCtrl = new GridBagConstraints();
		gbc_tglbtnLightCtrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnLightCtrl.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnLightCtrl.gridx = 0;
		gbc_tglbtnLightCtrl.gridy = 2;
		panel_3.add(tglbtnLightCtrl, gbc_tglbtnLightCtrl);
	}
	
	public void updateCountdownTimer (int i) {
		if (lblCtrlTime!=null) lblCtrlTime.setText(i+" s");
	}
	
	public void updateControlType (boolean auto) {
		tglbtnCtrl.setSelected(auto);
		if (auto) {
			tglbtnCtrl.setText("Automatic");
			lblCtrlTime.setText("60 s");
			lblCtrlTime.setEnabled(true);
		} else {
			tglbtnCtrl.setText("Manual");
			lblCtrlTime.setText("N/A");
			lblCtrlTime.setEnabled(false);
		}
		
		sliderFanCtrlMotion.setEnabled(auto);
		sliderFanCtrlTemp.setEnabled(auto);
		lblFanCtrlMotion.setEnabled(auto);
		lblFanCtrlTemp.setEnabled(auto);
		lblFanCtrlMotionValue.setEnabled(auto);
		lblFanCtrlTempValue.setEnabled(auto);
		
		sliderLightCtrlMotion.setEnabled(auto);
		sliderLightCtrlLight.setEnabled(auto);
		lblLightCtrlMotion.setEnabled(auto);
		lblLightCtrlLight.setEnabled(auto);
		lblLightCtrlMotionValue.setEnabled(auto);
		lblLightCtrlLightValue.setEnabled(auto);
		
		tglbtnFanCtrl.setEnabled(!auto);
		tglbtnLightCtrl.setEnabled(!auto);
	}
	public void updateFanButton(boolean on, boolean updateToggle) {
		if (tglbtnFanCtrl!=null) {
			if (on) tglbtnFanCtrl.setText("ON");
			else tglbtnFanCtrl.setText("OFF");
			
			if (updateToggle) tglbtnFanCtrl.setSelected(on);
		}
	}
	
	public void setFanButtonSelected(boolean flag) {
		if (tglbtnFanCtrl!=null) tglbtnFanCtrl.setSelected(flag);
	}
	
	public void updateLightButton(boolean on, boolean updateToggle) {
		if (tglbtnLightCtrl!=null) {
			if (on) tglbtnLightCtrl.setText("ON");
			else tglbtnLightCtrl.setText("OFF");
			
			if (updateToggle) tglbtnLightCtrl.setSelected(on);
		}
	}
	
	public void setLightButtonSelected(boolean flag) {
		if (tglbtnLightCtrl!=null) tglbtnLightCtrl.setSelected(flag);
	}
	
	public void setMotionReading(double value) {
		if (lblMotionReading!=null) lblMotionReading.setText(String.format("%.2f move/s",value));
	}
	
	public void setLightReading(double value) {
		if (lblLightReading!=null) lblLightReading.setText(String.format("%.2f %%",value));
	}
	
	public void setTemperatureReading(double value) {
		if (lblLightReading!=null) lblLightReading.setText(String.format("%.2f °C",value));
	}
	
	public void setFanAutomationMotionValue (double value, boolean updateSlider) {
		if (lblFanCtrlMotionValue!=null) lblFanCtrlMotionValue.setText(String.format("%.2f move/s",value));
		if (updateSlider) sliderFanCtrlMotion.setValue((int)(value*100.0));
	}
	
	public void setFanAutomationTemperatureValue (double value, boolean updateSlider) {
		if (lblFanCtrlTempValue!=null) lblFanCtrlTempValue.setText(String.format("%.2f °C",value));
		if (updateSlider) sliderFanCtrlTemp.setValue((int)(value*100.0));
	}
	
	public void setLightAutomationMotionValue (double value, boolean updateSlider) {
		if (lblLightCtrlMotionValue!=null) lblLightCtrlMotionValue.setText(String.format("%.2f move/s",value));
		if (updateSlider) sliderLightCtrlMotion.setValue((int)(value*100.0));
	}
	
	public void setLightAutomationLightValue (double value, boolean updateSlider) {
		if (lblLightCtrlLightValue!=null) lblLightCtrlLightValue.setText(String.format("%.2f %%",value));
		if (updateSlider) sliderLightCtrlLight.setValue((int)(value*100.0));
	}
	
}
