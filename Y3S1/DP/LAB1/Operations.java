package ro.uvt.dp;

public interface Operations {
	public double getTotalAmount();

	public double getInterest();

	public void depose(double amount);

	public void retrieve(double amount);
}
