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

    // OnClickListener Custom ----------------------------------------
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
    // OnClickListener Custom ----------------------------------------

    // OnLongClickListener Custom ------------------------------------
    public interface OnLongItemClickListener{
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener){
        this.onLongItemClickListener = listener;
    }
    // OnLongClickListener Custom ------------------------------------

    private ArrayList<NoticeItem> mList;

    /*================클릭이벤트================*//*
    //OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener{
        void onItemClicked(int position, String data);
    }

    //OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    //OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener){
        itemClickListener = listener;
    }


    *//*================클릭이벤트================*/

    public class NoticeUserViewHolder extends RecyclerView.ViewHolder{
        protected TextView notice;
        protected TextView registerDate;

        public NoticeUserViewHolder(@NonNull View view){
            super(view);
            this.notice = (TextView) view.findViewById(R.id.notice_user_name);  //CardView랑 연결하는거임.
            this.registerDate = (TextView) view.findViewById(R.id.notice_user_registerDate);

            /** 여기서 click listener 설정
             * https://kadosholy.tistory.com/59
             * 여기서 보고 하다가 멈췄음.**/

            /** https://taek2.tistory.com/14 */
            notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            notice.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (onLongItemClickListener != null){
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });

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
        viewHolder.registerDate.setText(mList.get(position).getRegisterDate());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    //클릭이벤트 처리
    public void onClickName(int position) {

    }

}
