package boleiros.gas_facil;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;

import boleiros.gas_facil.adapter.ProdutoAdapter;
import boleiros.gas_facil.favoritos.FragmentFavoritos;
import boleiros.gas_facil.login.LoginSignup;
import boleiros.gas_facil.perfil.Perfil;


public class Inicio extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Perfil.OnFragmentInteractionListener, FragmentFavoritos.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = "Recomendados";
                break;
            case 3:
                mTitle = "Favoritos";
                break;
            case 4:
                mTitle = getString(R.string.title_section2);
                break;
            case 5:
                mTitle = getString(R.string.title_section3);
                break;
            case 6:
                mTitle = "Sugestão";
                break;
            case 7:
                mTitle = "Fornecedor";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.inicio, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ParseUser.logOut();
            Intent intent = new Intent(Inicio.this, LoginSignup.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private RecyclerView mRecyclerView;
        private ProdutoAdapter mAdapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
//            mRecyclerView = (RecyclerView)rootView.findViewById(R.id.list);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//            mAdapter = new ProdutoAdapter(ProdutoManager.getInstance().getprodutos(), R.layout.card_layout, this.getActivity());
//            mRecyclerView.setAdapter(mAdapter);
//            mRecyclerView.addOnItemTouchListener(
//                    new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override public void onItemClick(View view, int position) {
//                            QuantidadeDeProdutoDialogo newFragment = new QuantidadeDeProdutoDialogo();
//                            ActivityStore.getInstance(getActivity()).setProduto(ProdutoManager.getInstance().getprodutos().get(position));
//                            newFragment.show(getFragmentManager(), "quantidade");
//                        }
//                    })
//            );

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Inicio) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public void carregaFragmentFavorito()
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new FragmentFavoritos())
                .commit();
    }

}
