package com.example.activity.fragmentTurn;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.activity.R;

public class Fragment3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // ����2�� ��Fragment�л�ȡ��������Fragment�Ŀؼ�
//		Button btnGetText=(Button)getActivity().findViewById(R.id.btnGetText);
//		btnGetText.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				TextView tv=(TextView)getActivity().findViewById(R.id.tvFragment1);
//				Toast.makeText(getActivity(), tv.getText().toString()	,Toast.LENGTH_SHORT).show();				
//			}
//		});
    }
}
