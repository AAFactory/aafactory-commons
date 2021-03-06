package mehdi.sakout.fancybuttons.samples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import io.github.aafactory.sample.R;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    String[] listItems = {"XML buttons", "Programmatically Buttons",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancybuttons_activity_main);

        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems));
        getListView().setOnItemClickListener(this);

        bindEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fancybuttons_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch(position){
            case 0 :
                Intent intentXML = new Intent(MainActivity.this,XmlButtons.class);
                startActivity(intentXML);

                break;
            case 1 :
                Intent intentProg = new Intent(MainActivity.this,ProgramButtons.class);
                startActivity(intentProg);
                break;
            default: throw new IllegalArgumentException("Hold up, hold my phone :)");
        }
    }

    private void bindEvent() {
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, io.github.aafactory.sample.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
