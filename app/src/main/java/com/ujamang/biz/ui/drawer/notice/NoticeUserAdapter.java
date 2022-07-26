package com.ujamang.biz.ui.drawer.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ujamang.biz.R;
import com.ujamang.biz.ui.drawer.codemanager.CodeAdapter;

import java.util.ArrayList;

public class NoticeUserAdapter extends RecyclerView.Adapter<NoticeUserAdapter.NoticeUserViewHolder> {
    //사내 공지사항 어댑터

    private ArrayList<NoticeItem> mList;

    public class NoticeUserViewHolder extends RecyclerView.ViewHolder{
        protected TextView notice;

        public NoticeUserViewHolder(@NonNull View view){
            super(view);
            this.notice = (TextView) view.findViewById(R.id.notice_user_name);  //CardView랑 연결하는거임.
        }
    }

    public NoticeUserAdapter(ArrayList<NoticeItem> mList) { //생성자
        this.mList = mList;
    }

    @NonNull
    @Override
    public NoticeUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_list_item, viewGroup, false);
        NoticeUserViewHolder viewHolder = new NoticeUserViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeUserViewHolder viewHolder, int position) {
        //실질적으로 데이터가 바인딩 되는 부분.. 그러나 여기가 아니라 NoticeActivity에서 데이터를 지정해서 이곳에 쏴주는 방법도 있다.
        //그 방법으로 해야할듯.
        viewHolder.notice.setText(mList.get(position).getNotice());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    //클릭이벤트 처리
    public void onClickName(int position) {

    }

}
