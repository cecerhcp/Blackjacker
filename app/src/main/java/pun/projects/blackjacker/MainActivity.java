package pun.projects.blackjacker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Random rand = new Random();
    private Boolean isPlaying = false;
    private Boolean isOver = false;
    private List<Integer> dealtCards = new ArrayList<Integer>();
    private Map<Integer, String> suits = new HashMap<Integer, String>();
    private Integer wins = 0;
    private Integer losses = 0;
    private Integer CLUBS = 0;
    private Integer HEARTS = 1;
    private Integer SPADES = 2;
    private Integer DIAMONDS = 3;
    private Integer score = 0;
    private Integer hardScore = 0;
    private Integer tableScore = 0;
    private Integer tableHardScore = 0;
    private TextView suitView1 = null;
    private TextView suitView2 = null;
    private TextView cardView = null;
    private TextView numberView = null;
    private TextView scoreView = null;
    private TextView tableView = null;
    private TextView lossesView = null;
    private TextView winsView = null;
    private Button hitButton = null;
    private Button stopButton = null;
    private Boolean hasAce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rand.setSeed(Calendar.getInstance().get(Calendar.SECOND));
        suits.put(CLUBS, "♣");
        suits.put(HEARTS, "♥");
        suits.put(SPADES, "♠");
        suits.put(DIAMONDS, "♦");
        setContentView(R.layout.activity_main);
        scoreView = (TextView) findViewById(R.id.scoreView);
        tableView = (TextView) findViewById(R.id.tableView);
        cardView = (TextView) findViewById(R.id.cardView);
        suitView1 = (TextView) findViewById(R.id.suitView1);
        suitView2 = (TextView) findViewById(R.id.suitView2);
        numberView = (TextView) findViewById(R.id.numberView);
        winsView = (TextView) findViewById(R.id.winsView);
        lossesView = (TextView) findViewById(R.id.lossesView);

        hitButton = (Button) findViewById(R.id.hitButton);
        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOver) return;

                if (isPlaying)
                {
                    Integer card = pickACard();
                    String cardText = new Integer((card % 13) + 1).toString();
                    String cardSuit = suits.get(card / 13);
                    changeSuits(card / 13);
                    Integer cardVal = (card % 13) + 1;
                    if (cardVal == 1) cardText = "A";
                    else if (cardVal == 11) cardText = "J";
                    else if (cardVal == 12) cardText = "Q";
                    else if (cardVal == 13) cardText = "K";
                    numberView.setText(cardText);
                    addScore(cardVal);
                    if (hasAce) {
                        scoreView.setText("Score: " + score.toString() + "/" + hardScore.toString());
                    }
                    else
                    {
                        scoreView.setText("Score: " + score.toString());
                    }
                    checkGame();
                }
                else {
                    hitButton.setText("Hit!");
                    stopButton.setText("Stop!");
                    isPlaying = true;
                }

            }
        });
        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying)
                {
                    resetGame();
                }
                else
                {
                    tablePlay();
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void checkGame()
    {
        if (score > 21 && hardScore > 21)
        {
            cardView.setText("YOU LOSE!");
            losses += 1;
            lossesView.setText("Losses: " + losses.toString());
            stopButton.setText("Reset");
            isPlaying = false;
            isOver = true;

        }


    }

    public void tablePlay()
    {
        tableScore = rand.nextInt(28 - 15 + 1) + 15;
        tableView.setText("Table: " + tableScore.toString());
        Integer playerScore;
        if (hardScore > 21) playerScore = score;
        else playerScore = hardScore;

        if (tableScore > 21 || playerScore > tableScore)
        {
            cardView.setText("YOU WIN!");
            wins += 1;
            winsView.setText("Wins: " + wins.toString());

        }
        else
        {
            cardView.setText("YOU LOSE!");
            losses += 1;
            lossesView.setText("Losses: " + losses.toString());
        }
        stopButton.setText("Reset");
        isOver = true;
        isPlaying = false;
    }

    public void resetGame()
    {
        hitButton.setText("Start");
        cardView.setText("");
        suitView1.setText("?");
        suitView2.setText("?");
        numberView.setText("X");
        score = 0;
        hardScore = 0;
        scoreView.setText("Score: 0");
        tableView.setText("Table: 0");
        isOver = false;
        hasAce = false;
        dealtCards.clear();

    }

    public void addScore(int card)
    {
        if (card == 1 && !hasAce)
        {
            hasAce = true;
            score += 1;
            hardScore += 11;
        }
        else if (card == 1 && hasAce)
        {
            score += 1;
            hardScore += 1;
        }
        else if (card >= 10)
        {
            score += 10;
            hardScore += 10;
        }
        else
        {
            score += card;
            hardScore += card;
        }

    }
    public int pickACard()
    {
        int card = rand.nextInt(53);
        while (dealtCards.contains(card))
        {
            card = rand.nextInt(53);
        }
        dealtCards.add(card);
        return card;

    }

    public void changeSuits(int suit) {
        if (suit == CLUBS) {
            suitView1.setText("♣");
            suitView2.setText("♣");
        }
        else if (suit == HEARTS)
        {
            suitView1.setText("♥");
            suitView2.setText("♥");
        }
        else if (suit == SPADES)
        {
            suitView1.setText("♠");
            suitView2.setText("♠");
        }
        else if (suit == DIAMONDS)
        {
            suitView1.setText("♦");
            suitView2.setText("♦");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
