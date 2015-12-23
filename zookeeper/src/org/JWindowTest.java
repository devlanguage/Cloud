package org;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
class FormattedTextFieldVerifier extends InputVerifier {
    public boolean verify(JComponent input) {
        if (input instanceof JFormattedTextField) {
            System.out.println(3333);
            JFormattedTextField ftf = (JFormattedTextField)input;
            AbstractFormatter formatter = ftf.getFormatter();
            if (formatter != null) {
                String text = ftf.getText();
                try {
                     formatter.stringToValue(text);
                     return true;
                 } catch (ParseException pe) {
                     return false;
                 }
             }
         }
         return true;
     }
     public boolean shouldYieldFocus(JComponent input) {
         return verify(input);
     }
 }

public class JWindowTest {
    public static void main(String[] args) {
        Double d2 = new Double(+1);
        Double d1 = new Double(-1);
        JFrame frame = new JFrame("Spinner");
        frame.setDefaultCloseOperation(3);
        
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpinnerModel source = (SpinnerModel) e.getSource();
                System.out.println("The value is: " + source.getValue());
            }
        };
        

//        String[] months = new DateFormatSymbols().getMonths();
//        SpinnerModel model = new SpinnerListModel(months);
//        JSpinner spinner = new JSpinner(model);
//        frame.getContentPane().add(spinner, BorderLayout.NORTH);
//                model.addChangeListener(listener);

        JFormattedTextField tfd = new JFormattedTextField();
        tfd.setColumns(100);
        tfd.setInputVerifier(new FormattedTextFieldVerifier());
        frame.getContentPane().add(tfd, BorderLayout.NORTH);

        SpinnerNumberModel model2 = new SpinnerNumberModel(50, 0, 100, 5);
        JSpinner spinner2 = new JSpinner(model2);
        frame.getContentPane().add(spinner2, BorderLayout.CENTER);
//        model2.setValue("TEST");

        SpinnerDateModel model3 = new SpinnerDateModel();
        model3.setCalendarField(Calendar.WEEK_OF_MONTH);
        JSpinner spinner3 = new JSpinner(model3);
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(spinner3, "MMMMM dd, yyyy");
        spinner3.setEditor(editor2);
        frame.getContentPane().add(spinner3, BorderLayout.SOUTH);
        
        

     
        model3.addChangeListener(listener);
        model2.addChangeListener(listener);

        frame.pack();
        frame.setVisible(true);

    }
}
