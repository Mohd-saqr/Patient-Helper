package com.patient.patienthelper.adapters;//package com.patient.patienthelper.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.PopupMenu;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amplifyframework.datastore.generated.model.Task;
//import com.patient.patienthelper.R;
//import com.patient.patienthelper.api.Drug;
//
//import net.gg.myapplication.R;
//
//import java.util.List;
//
//
//public  class RecyclerViewAdapterMyDrugs extends RecyclerView.Adapter<RecyclerViewAdapterMyDrugs.MyViewHolder> {
//
////    List<Task> tasks;
//    List<Drug> drugs;
//    itemClickL itemClickL;
//    deleteIconClickLester deleteIconClickLester;
//    EditIconClickLester editIconClickLester;
//    ItemMenuLis itemMenuLis;
//    PopupMenu popupMenu;
//
//
//
//    public RecyclerViewAdapterMyDrugs(List<Drug> tasks ){
//        this.drugs=tasks;
//        this.itemClickL=itemClickL;
//
//    }
//    public RecyclerViewAdapterMyDrugs(itemClickL itemClickL, List<Drug> tasks
//            , ItemMenuLis itemMenuLis){
//        this.drugs=tasks;
//        this.itemMenuLis=itemMenuLis;
//        this.itemClickL=itemClickL;
//    }
//    public RecyclerViewAdapterMyDrugs(List<Drug> tasks, itemClickL itemClickL, deleteIconClickLester deleteIconClickLester){
//        this.drugs=tasks;
//        this.itemClickL=itemClickL;
//        this.deleteIconClickLester=deleteIconClickLester;
//    }
//    public RecyclerViewAdapterMyDrugs(List<Drug> tasks, itemClickL itemClickL, deleteIconClickLester deleteIconClickLester, EditIconClickLester editIconClickLester){
//        this.drugs=tasks;
//        this.itemClickL=itemClickL;
//        this.deleteIconClickLester=deleteIconClickLester;
//        this.editIconClickLester=editIconClickLester;
//    }
//    public RecyclerViewAdapterMyDrugs(itemClickL itemClickL, deleteIconClickLester deleteIconClickLester, EditIconClickLester editIconClickLester){
//
//        this.itemClickL=itemClickL;
//        this.deleteIconClickLester=deleteIconClickLester;
//        this.editIconClickLester=editIconClickLester;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
//       View view= layoutInflater.inflate(R.layout.my_row,parent,false);
//
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewAdapterMyDrugs.MyViewHolder holder, int position) {
//
////        holder.title.setText(tasks.get(position).getTitle());
////        holder.body.setText(tasks.get(position).getBody());
////        holder.sate.setText(tasks.get(position).getState().toString());
////        holder.itemView.setOnClickListener(v -> {
////            itemClickL.OnItemClick(tasks.get(position));
////        });
////        holder.menu.setOnClickListener(v -> {
////             popupMenu = new PopupMenu(v.getContext(),v);
////            popupMenu.inflate(R.menu.recycler_view_menu);
////            popupMenu.show();
////            itemMenuLis.OnCreateItemMenuTest(popupMenu,tasks.get(position));
////        });
//
//
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return tasks.size();
//    }
//
//
//
//
//    public interface itemClickL{
//        void OnItemClick(com.amplifyframework.datastore.generated.model.Task task);
//    }
//
//    public interface deleteIconClickLester{
//        void onDeleteClick(com.amplifyframework.datastore.generated.model.Task task);
//    }
//    public interface EditIconClickLester{
//        void onEditClick(com.amplifyframework.datastore.generated.model.Task task);
//    }
//    public interface ItemMenuLis{
//        void OnCreateItemMenuTest(PopupMenu popupMenu,com.amplifyframework.datastore.generated.model.Task task);
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder  {
//
//        TextView title;
//        TextView body;
//        TextView sate;
//        ImageView menu;
//
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            menu=itemView.findViewById(R.id.recycler_menu_icon);
//            title= itemView.findViewById(R.id.text_view_title);
//            body=itemView.findViewById(R.id.text_view_body);
//            sate=itemView.findViewById(R.id.text_view_state);
//
//
//
//
//
//        }
//
//    }
//
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//
//
//    public void updateData(List<Task> list){
//        tasks.clear();
//        tasks.addAll(list);
//        notifyDataSetChanged();
//    }
//
//
//
//
//
//
//
//
//
//
//}
