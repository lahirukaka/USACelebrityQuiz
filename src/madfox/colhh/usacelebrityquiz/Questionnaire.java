package madfox.colhh.usacelebrityquiz;

public final class Questionnaire {

	private String[] ques;
	private String[][] answers;
	private int[] corrects;
	
	static Questionnaire instance;
	
	/**
	 * Lazy Initialized Object - Call this method to get the only instance of this class
	 * @return single object
	 */
	static synchronized Questionnaire getInstance()
	{
		if(instance == null)
		{
			instance = new Questionnaire();
		}
		return instance;
	}
	/**
	 * @return the ques
	 */
	public String getQues(int position) {
		return ques[position];
	}
	/**
	 * @param ques the ques to set
	 */
	public Questionnaire setQues(String[] ques) {
		this.ques = ques;
		return this;
	}
	/**
	 * @return the answers
	 */
	public String[] getAnswers(int positions) {
		return answers[positions];
	}
	/**
	 * @param answers the answers to set
	 */
	public Questionnaire setAnswers(String[][] answers) {
		this.answers = answers;
		return this;
	}
	/**
	 * @return the corrects
	 */
	public int getCorrects(int position) {
		return corrects[position];
	}
	/**
	 * @param corrects the corrects to set
	 */
	public Questionnaire setCorrects(int[] corrects) {
		this.corrects = corrects;
		return this;
	}
	
	
}
