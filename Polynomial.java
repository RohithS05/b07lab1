import java.lang.Math;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

class Polynomial {
    double [] co_array;
    int [] exp_array;

    public Polynomial(){
	this.co_array = new double [1];
	co_array[0] = 0;
	this.exp_array = new int [1];
	exp_array[0] = 0;
    }

    public Polynomial(double[] co_array, int[] exp_array){
	this.co_array = new double [co_array.length];
	this.exp_array = new int [exp_array.length];
	for (int i = 0; i < co_array.length; i++){
	    this.co_array[i] = co_array[i];
	    this.exp_array[i] = exp_array[i];
	}
    }

    public Polynomial(int[] exp_array, double[] co_array){
	this.co_array = new double [co_array.length];
	this.exp_array = new int [exp_array.length];
	for (int i = 0; i < co_array.length; i++){
	    this.co_array[i] = co_array[i];
	    this.exp_array[i] = exp_array[i];
	}
    }

    public Polynomial(File file) throws FileNotFoundException, IOException {
	BufferedReader input = new BufferedReader(new FileReader(file.getAbsolutePath()));
	String line = input.readLine();
	input.close();

	if (line != null){
	    parse_poly(line);
	}
    }

    public boolean inside(char val, String string){
	for (int i = 0; i < string.length(); i++){
	    char c = string.charAt(i);
	    if (c == val){
		return true;
	    }
	}
	return false;
    }

    public void parse_poly(String string){
	String[] terms = string.split("(?=\\+)|(?=-)");
	double [] co_array = new double [terms.length];
	int [] exp_array = new int [terms.length];


	for (int i = 0; i < terms.length; i++){
	    if (inside('x', terms[i]) == false){
		co_array[i] = Double.parseDouble(terms[i]);
		exp_array[i] = 0;
		continue;
	    }
	    String[] temp_term = terms[i].split("x");
	    co_array[i] = Double.parseDouble(temp_term[0]);
	    exp_array[i] = Integer.parseInt(temp_term[1]);
	}

	this.co_array = new double [co_array.length];
	this.exp_array = new int [exp_array.length];
	for (int i = 0; i < co_array.length; i++){
	    this.co_array[i] = co_array[i];
	    this.exp_array[i] = exp_array[i];
	}
    }

    public boolean inside(int val, int [] array){
	for (int i = 0; i < array.length; i++){
	    if (val == array[i])
		return true;
	}
	return false;
    }

    public Polynomial remove_zero(double [] co_array, int[] exp_array){
	int length = exp_array.length;

	int [] new_exp_array = new int [length];
	double [] new_co_array = new double [length];

	int j = 0;

	for (int i = 0; i < length; i++){
	    if (co_array[i] != 0){
		new_co_array[j] = co_array[i];
		new_exp_array[j] = exp_array[i];
		j++;
	    }
	}

	if (j == 0){
	    Polynomial return_poly = new Polynomial();
	    return return_poly;
	}

	Polynomial return_poly = new Polynomial(new_co_array, new_exp_array);
	return return_poly;
    }

    public Polynomial add(Polynomial polynomial){

	int length = Math.max(co_array.length, (polynomial.co_array).length);
	double [] new_co_array = new double [length];
	int [] new_exp_array = new int [length];

	int j = 0;
	int i = 0;
	boolean flag = false;

	while (i < length){
	    flag = false;

	    int exp;
	    if (i < exp_array.length)
		exp = exp_array[i];
	    else {
		exp = (polynomial.exp_array)[i];
	    }

	    if (inside(exp, new_exp_array))
		continue;
	    else
		new_exp_array[j]=exp;
		flag = true;

	    if (inside(exp, exp_array) && inside(exp, polynomial.exp_array)){
		if (i >= co_array.length) {
		    new_co_array[j] = polynomial.co_array[i];
		}
		else if (i >= (polynomial.co_array).length) {
		    new_co_array[j] = co_array[i];
		}
		else {
		    new_co_array[j] = co_array[i] + polynomial.co_array[i];
		}
	    }

	    else  if (inside(exp, exp_array)){
		new_co_array[j] = co_array[i];
	    }

	    else {
		new_co_array[j] = polynomial.co_array[i];
	    }

	    i = i+1;
	    if (flag)
		j = j+1;

	}

	Polynomial return_poly = remove_zero(new_co_array, new_exp_array);
	return return_poly;
    }

    public double evaluate(double x){
	int length = co_array.length;
	double counter = 0;

	for (int i = 0; i < length; i++){
	    int exp = exp_array[i];
	    double co = co_array[i];

	    counter += co*(Math.pow(x, exp));
	}

	return counter;
    }

    public boolean hasRoot(double val){
	if (this.evaluate(val) == 0){
	    return true;
	}
	return false;
    }

    public Polynomial multiply(Polynomial polynomial){
	int length = Math.max(co_array.length, (polynomial.co_array).length);
	double [] new_co_array = new double [length];
	int [] new_exp_array = new int [length];
	Polynomial return_poly = new Polynomial(new_co_array, new_exp_array);

	for (int i = 0; i < co_array.length; i++){
	    int exp = exp_array[i];
	    double val = co_array[i];

	    double [] temp_co_array = new double [length];
	    int [] temp_exp_array = new int [length];

	    for (int j = 0; j < (polynomial.co_array).length; j++){
		temp_co_array[j] = val*(polynomial.co_array[j]);
		temp_exp_array[j] = exp+(polynomial.exp_array[j]);

	    }

	    Polynomial temp_poly = remove_zero(temp_co_array, temp_exp_array);
	    return_poly = new Polynomial(new_co_array, new_exp_array);
	    return_poly = return_poly.add(temp_poly);
	}

	return return_poly;
    }

    public void saveToFile(String file_name){
	String temp_poly = "";
	int length = co_array.length;
	for (int i = 0; i < length; i++){
	    if (exp_array[i] == 0){
		if (co_array[i] > 0){
		    temp_poly += "+";
		}
		temp_poly += Double.toString(co_array[i]);
		continue;
	    }

	    if (co_array[i] > 0){
		temp_poly += "+";
	    }
	    temp_poly += Double.toString(co_array[i]);
	    temp_poly += "x";
	    temp_poly += Integer.toString(exp_array[i]);
	}
	if (temp_poly.charAt(0) == '+'){
	    temp_poly = temp_poly.substring(1);
	}

	try {
	    FileWriter myWriter = new FileWriter(file_name);
	    myWriter.write(temp_poly);
	    myWriter.close();
	} catch (IOException e){
	}
    }

}
