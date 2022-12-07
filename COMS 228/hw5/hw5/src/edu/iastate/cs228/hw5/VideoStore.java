package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner; 

/**
 * 
 * @author Khanh Tran
 *
 */

public class VideoStore 
{
	protected SplayTree<Video> inventory = new SplayTree<>(); // all the videos at the store     
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement. 
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
    	setUpInventory(videoFile); 
    }
    
    
   /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
     * add videos to the inventory by calling the addBST() method, which does not splay. 
     * 
     * Refer to Section 3.2 for the format of video file. 
     * 
     * @param  videoFile  correctly formated if exists
     * @throws FileNotFoundException 
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
    	bulkImport(videoFile);
   	
    }
	
    
    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film
     * @return
     */
	public Video findVideo(String film) 
	{
		Video a = new Video (film);
		return inventory.findElement(a); 
	}


	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		Video vid = new Video (film, n);
		if (inventory.add(vid) == false) {
			Video vide = inventory.findElement(vid);
			vide.addNumCopies(n);
		}
		
	}
	

	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		addVideo(film, 1);
	}
	

	/**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
    	File f = new File (videoFile);
    	Scanner in = new Scanner (f);
    	while (in.hasNextLine()) {
    		String vidName = in.nextLine();
    		Video vid = new Video(parseFilmName(vidName), parseNumCopies(vidName));
    		inventory.addBST(vid);
    	}
    	in.close();	
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film)
	{
		 Video vid = new Video(film);
		if (findVideo(film) == null) {
			return false;
		}
			return true;
	}

	
	
	/**
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,  
									     			 AllCopiesRentedOutException 
	{
		
		Video vid = inventory.findElement(new Video(film, 1));
		
		if (n <= 0 || findVideo(film) == null || film.isEmpty()) {
			throw new IllegalArgumentException("Please enter valid!");
		}
		else if (findVideo(film) == null) {
			throw new FilmNotInInventoryException("Film not in inventory");
		}
		else if (!available(film)) {
			throw new FilmNotInInventoryException ("Out of copies");
		}
		else if (available(film)){
			vid.rentCopies(n);
		}
		
	}

	
	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, 
												  FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		File f = new File (videoFile);
    	Scanner in = new Scanner (f);
    	while (in.hasNextLine()) {
    		String line = in.nextLine();
    		videoRent(parseFilmName(line), parseNumCopies(line));
    	}
    	in.close();	
    }

	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		if (n <= 0) {
			throw new IllegalArgumentException("Cannot process a negative number request!");
		}
		else if (film == null || film.isEmpty()) {
			throw new IllegalArgumentException("Please enter a valid movie title!");
		}
		
		Video vid = new Video (film, n);
		Video videoRented = inventory.findElement(vid);
		if (videoRented == null) {
			throw new FilmNotInInventoryException("The movie: " + film + " is no longer in inventory!");
		}
		else {
			if (n <= videoRented.getNumRentedCopies()) {
				videoRented.returnCopies(n);
			}
			else if (n > videoRented.getNumRentedCopies()) {
				videoRented.returnCopies(videoRented.getNumCopies());
			}
		}
		
	}
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
													FilmNotInInventoryException												
	{
		File f = new File (videoFile);
    	Scanner in = new Scanner (f);
    	while (in.hasNextLine()) {
    		String line = in.nextLine();
    		videoReturn(parseFilmName(line), parseNumCopies(line));
    	}
    	in.close();	
    }
	
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return
	 */
	public String inventoryList()
	{
		
		String initialLine = "Films in inventory: \n";
		Iterator<Video> iter = inventory.iterator();
		String result = "";
		while (iter.hasNext()) {
			Video video = iter.next();
			String videoName = video.getFilm();
			int copies = video.getNumCopies();
			result += videoName + " (" + copies + ")" + "\n";
		}
		
		return initialLine + result;
	}

	
	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return 
	 */
	public String transactionsSummary()
	{
		return rentedVideosList() + "\n\n" + unrentedVideosList(); 
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList()
	{
		Iterator<Video> iter = inventory.iterator();
		String result = "";
		String iniLine = "Rented films: \n";
		while (iter.hasNext()) {
			Video vid = iter.next();
			if (vid.getNumRentedCopies() == 0) {
				break;
			}
			else {
				String videoName = vid.getFilm();
				int copies = vid.getNumRentedCopies();
				result += videoName + " (" + copies + ")" + "\n";
			}
		}
		return iniLine + result; 
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList()
	{
		Iterator<Video> iter = inventory.iterator();
		String result = "";
		String iniLine = "Films remaining in inventory: \n";
		while (iter.hasNext()) {
			Video vid = iter.next();
			if (vid.getNumCopies() == 0) {
				break;
			}
			else {
				String videoName = vid.getFilm();
				int copies = vid.getNumAvailableCopies();
				result += videoName + " (" + copies + ")" + "\n";
			}
		}
		return iniLine + result; 
	}	

	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) 
	{
		String result = "";
		for (int i = 0; i < line.length(); i++) {
			if (Character.isLetter(line.charAt(i))) {
				result += line.charAt(i);
			}
			if (Character.isWhitespace(line.charAt(i))) {
				result += " ";
			}
		}
		return result.trim();
}
	
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) 
	{
		char a = ' ';
		for (int i = 0; i < line.length(); i++) {
			if (Character.isDigit(line.charAt(i))) {
				 a = line.charAt(i);
			}
		}
		if (Character.getNumericValue(a) == -1) {
			return 1;
		}
		return Character.getNumericValue(a);
	}
}
