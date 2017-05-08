package com.lorusso.luca.labeling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ProtocolAdapter extends
        RecyclerView.Adapter<ProtocolAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView protocolTextView;
        public TextView idTextView;
        public TextView descriptionTextView;
        public Button continueButton;
        String user = null;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            protocolTextView = (TextView) itemView.findViewById(R.id.exerc);
            idTextView = (TextView) itemView.findViewById(R.id.number);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            continueButton = (Button) itemView.findViewById(R.id.buttonContinue);
            itemView.findViewById(R.id.buttonContinue).setOnClickListener(this);
            user = ((Activity) mContext).getIntent().getStringExtra("user");


        }

        @Override
        public void onClick(View v) {

            Toast.makeText(itemView.getContext(), "The Item Clicked is: " + getPosition(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(idTextView.getContext(), DataConstraint.class);
            i.putExtra("user",user);
            i.putExtra("protocol", this.getProtocol(getPosition()));
            i.putExtra("descProtocol", this.getProtocol(getPosition()).getDescrizione());
            itemView.getContext().startActivity(i);
        }

        public Protocol getProtocol(int position) {
            return protocols.get(position);
        }
    }

    public List<Protocol> getProtocols() {
        return protocols;
    }


    private static List<Protocol> protocols;
    // Store the context for easy access
    private static Context mContext;

    // Pass in the contact array into the constructor
    public ProtocolAdapter(Context context, List<Protocol> protocols) {
        this.protocols = protocols;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public ProtocolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.rows, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public void onBindViewHolder(ProtocolAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Protocol protocol = protocols.get(position);
        final String user = ((Activity) mContext).getIntent().getStringExtra("user");
        // Set item views based on your views and data model
        TextView protocolTextView = viewHolder.protocolTextView;
        protocolTextView.setText("Protocol");

        TextView idTextView = viewHolder.idTextView;
        idTextView.setText(protocol.getIdProtocol());

        TextView descriptionTextView = viewHolder.descriptionTextView;
        descriptionTextView.setText(protocol.getDescrizione());

        Button button = viewHolder.continueButton;


    }

    @Override
    public int getItemCount() {
        return protocols.size();
    }
}

