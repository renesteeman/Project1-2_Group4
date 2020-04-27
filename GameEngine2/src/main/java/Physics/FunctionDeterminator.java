package Physics;

//TODO add explanation. What is meant with a function?
public class FunctionDeterminator {
	public static Function2d getFunction(String s) {
		return new HeightFunction(s);
	}
}
