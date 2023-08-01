package calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import calculator.of.my.name.R;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    String firstNumber;
    String secondNumber;
    Double result;
    String op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        op = "";
    }


    public void clickNumberOne(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("1");
        } else {
            editText.setText(editText.getText().toString() + 1);
        }
    }

    public void clickNumberTwo(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("2");
        } else {
            editText.setText(editText.getText().toString() + 2);
        }
    }

    public void clickNumberTree(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("3");
        } else {
            editText.setText(editText.getText().toString() + 3);
        }
    }

    public void clickNumberFour(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("4");
        } else {
            editText.setText(editText.getText().toString() + 4);
        }
    }

    public void clickNumberFive(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("5");
        } else {
            editText.setText(editText.getText().toString() + 5);
        }
    }

    public void clickNumberSix(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("6");
        } else {
            editText.setText(editText.getText().toString() + 6);
        }
    }

    public void clickNumberSeven(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("7");
        } else {
            editText.setText(editText.getText().toString() + 7);
        }
    }

    public void clickNumberEight(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("8");
        } else {
            editText.setText(editText.getText().toString() + 8);
        }
    }

    public void clickNumberNine(View view) {
        if (editText.getText().toString().equals("0")) {
            editText.setText("9");
        } else {
            editText.setText(editText.getText().toString() + 9);
        }
    }

    public void clickNumberO(View view) {
        if (!(editText.getText().toString().equals("0"))) {
            editText.setText(editText.getText().toString() + 0);
        }
    }

    public void clickNumberDot(View view) {
        if (editText.getText().toString().equals("0") || editText.getText().toString().isEmpty()) {
            editText.setText("0.");
        }
        if (!editText.getText().toString().contains(".")) {
            editText.setText(editText.getText().toString() + ".");
        }
    }

    public void clickNumberPlusMinus(View view) {
        if (!editText.getText().toString().contains("-")) {
            editText.setText("-" + editText.getText().toString());
        } else {
            editText.setText(editText.getText().toString().replaceAll("-", ""));
        }
    }


    public void clickDivide(View view) {
        if (op.equals("")) {
            firstNumber = editText.getText().toString();
            editText.setText("");
            editText.setHint(firstNumber + "/");
            op = "/";
        } else {
            editText.setHint(firstNumber + "/");
            op = "/";
        }
    }

    public void clickMultiplication(View view) {
        if (op.equals("")) {
            firstNumber = editText.getText().toString();
            editText.setText("");
            editText.setHint(firstNumber + "*");
            op = "*";
        } else {
            editText.setHint(firstNumber + "*");
            op = "*";
        }
    }

    public void clickPlus(View view) {
        if (op.equals("")) {
            firstNumber = editText.getText().toString();
            editText.setText("");
            editText.setHint(firstNumber + "+");
            op = "+";
        } else {
            editText.setHint(firstNumber + "+");
            op = "+";
        }
    }

    public void clickMinus(View view) {
        if (op.equals("")) {
            firstNumber = editText.getText().toString();
            editText.setText("");
            editText.setHint(firstNumber + "-");
            op = "-";
        } else {
            editText.setHint(firstNumber + "-");
            op = "-";
        }
    }

    public void clickEquals(View view) {
        secondNumber = editText.getText().toString();

            switch (op) {
                case "-":
                    result = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                    break;
                case "+":
                    result = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                    break;
                case "/":
                    if (secondNumber.equals("0")) {
                        clickAC(view);
                        editText.setHint("На 0 делить нельзя");
                        return;
                    }
                    result = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                    break;
                case "*":
                    result = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                    break;
            }
            editText.setText(result.toString());
            op = "";
    }

    public void clickAC(View view) {
        editText.setHint("0");
        editText.setText("");
        op = "";
    }

    public void clickProcent(View view) {
        if (op == "") {
            firstNumber = editText.getText().toString();
            result = Double.parseDouble(firstNumber) / 100;
        } else {
            secondNumber = editText.getText().toString();
            switch (op) {
                case "-":
                    result = Double.parseDouble(firstNumber) - (Double.parseDouble(firstNumber) / 100) * Double.parseDouble(secondNumber);
                    break;
                case "+":
                    result = Double.parseDouble(firstNumber) + (Double.parseDouble(firstNumber) / 100) * Double.parseDouble(secondNumber);
                    break;
                case "/":
                    result = Double.parseDouble(firstNumber) * ((Double.parseDouble(firstNumber) / 100) * Double.parseDouble(secondNumber));
                    break;
                case "*":
                    result = Double.parseDouble(firstNumber) / ((Double.parseDouble(firstNumber) / 100) * Double.parseDouble(secondNumber));
                    break;
            }
        }
        editText.setText(result.toString());
        op = "";
    }
}