package tt1.java;

import java.lang.Math;

class Polynomial {
    double [] array;

    public Polynomial(){
        this.array = new double [1];
        array[0] = 0;
    }
    public Polynomial(double[] array){
        this.array = new double [array.length];
        for (int i = 0; i < array.length; i++){
            this.array[i] = array[i];
        }
    }

    public Polynomial add(Polynomial polynomial){
        int length = Math.max(array.length, (polynomial.array).length);
        double [] new_array = new double [length];
        for (int i = 0; i < length; i++){
        	if (i >= array.length) {
        		new_array[i] = polynomial.array[i];
        	}
        	else if (i >= polynomial.array.length) {
        		new_array[i] = array[i];
        	}
        	else {
        		new_array[i] = array[i] + polynomial.array[i];
        	}
        }
        Polynomial return_poly = new Polynomial(new_array);
        return return_poly; 
    }

    public double evaluate(double x){
        double counter = array[0];
        double val = 1;
        for (int i = 1; i < array.length; i++){
        	for (int j = 0; j < i; j++) {
        		val *= x;
        	}
            counter += array[i]*val;
            val = 1;
        }
        return counter;
    }
    
	public double evaluate(int x) {
        double y = x;
        return evaluate(y);
	}

    public boolean hasRoot(double val){
        if (this.evaluate(val) == 0){
            return true;
        }
        return false;
    }


}
