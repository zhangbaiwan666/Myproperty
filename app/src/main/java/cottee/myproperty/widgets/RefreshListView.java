package cottee.myproperty.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.text.SimpleDateFormat;

import cottee.myproperty.R;
import cottee.myproperty.listener.OnRefreshListener;

public class RefreshListView extends ListView implements OnScrollListener {

	private static final String TAG = "RefreshListView";
	private int firstVisibleItemPosition;
	private int downY;
	private int headerViewHeight;
	private View headerView;

	private final int DOWN_PULL_REFRESH = 0;
	private final int RELEASE_REFRESH = 1;
	private final int REFRESHING = 2;
	private int currentState = DOWN_PULL_REFRESH;

	private Animation upAnimation;
	private Animation downAnimation;

	private ImageView ivArrow; // ͷ���ֵļ�ͷ
	private ProgressBar mProgressBar; // ͷ���ֵĽ�����
	private TextView tvState; // ͷ���ֵ�״̬
	private TextView tvLastUpdateTime; // ͷ���ֵ�������ʱ��

	private OnRefreshListener mOnRefershListener;
	private boolean isScrollToBottom; // �Ƿ񻬶����ײ�
	private View footerView; // �Ų��ֵĶ���
	private int footerViewHeight; // �Ų��ֵĸ߶�
	private boolean isLoadingMore = false; // �Ƿ����ڼ��ظ�����


	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
		this.setOnScrollListener(this);
	}


	private void initFooterView() {
		footerView = View.inflate(getContext(), R.layout.listview_footer, null);
		footerView.measure(0, 0);
		footerViewHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		this.addFooterView(footerView);
	}


	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.listview_header, null);
		ivArrow = (ImageView) headerView.findViewById(R.id.iv_listview_header_arrow);
		mProgressBar = (ProgressBar) headerView.findViewById(R.id.pb_listview_header);
		tvState = (TextView) headerView.findViewById(R.id.tv_listview_header_state);
		tvLastUpdateTime = (TextView) headerView.findViewById(R.id.tv_listview_header_last_update_time);


		tvLastUpdateTime.setText("最近更新时间" + getLastUpdateTime());

		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		this.addHeaderView(headerView);
		initAnimation();
	}


	private String getLastUpdateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}

	/**
	 * ��ʼ������
	 */
	private void initAnimation() {
		upAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true); // ����������, ͣ���ڽ�����λ����

		downAnimation = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true); // ����������, ͣ���ڽ�����λ����
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getY();
			// �ƶ��е�y - ���µ�y = ���.
			int diff = (moveY - downY) / 2;
			// -ͷ���ֵĸ߶� + ��� = paddingTop
			int paddingTop = -headerViewHeight + diff;
			// ���: -ͷ���ֵĸ߶� > paddingTop��ֵ ִ��super.onTouchEvent(ev);
			if (firstVisibleItemPosition == 0 && -headerViewHeight < paddingTop) {
				if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) { // ��ȫ��ʾ��.
					Log.i(TAG, "�ɿ�ˢ��");
					currentState = RELEASE_REFRESH;
					refreshHeaderView();
				} else if (paddingTop < 0 && currentState == RELEASE_REFRESH) { // û����ʾ��ȫ
					Log.i(TAG, "����ˢ��");
					currentState = DOWN_PULL_REFRESH;
					refreshHeaderView();
				}
				headerView.setPadding(0, paddingTop, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			// �жϵ�ǰ��״̬���ɿ�ˢ�»�������ˢ��
			if (currentState == RELEASE_REFRESH) {
				Log.i(TAG, "ˢ������.");
				// ��ͷ��������Ϊ��ȫ��ʾ״̬
				headerView.setPadding(0, 0, 0, 0);
				// ���뵽����ˢ����״̬
				currentState = REFRESHING;
				refreshHeaderView();

				if (mOnRefershListener != null) {
					mOnRefershListener.onDownPullRefresh(); // ����ʹ���ߵļ�������
				}
			} else if (currentState == DOWN_PULL_REFRESH) {
				// ����ͷ����
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ����currentStateˢ��ͷ���ֵ�״̬
	 */
	private void refreshHeaderView() {
		switch (currentState) {
		case DOWN_PULL_REFRESH: // ����ˢ��״̬
			tvState.setText("加载最新");
			ivArrow.startAnimation(downAnimation); // ִ��������ת
			break;
		case RELEASE_REFRESH: // �ɿ�ˢ��״̬
			tvState.setText("松手即可刷新");
			ivArrow.startAnimation(upAnimation); // ִ��������ת
			break;
		case REFRESHING: // ����ˢ����״̬
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			tvState.setText("努力加载中...");
			break;
		default:
			break;
		}
	}

	/**
	 * ������״̬�ı�ʱ�ص�
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
			// �жϵ�ǰ�Ƿ��Ѿ����˵ײ�
			if (isScrollToBottom && !isLoadingMore) {
				isLoadingMore = true;
				// ��ǰ���ײ�
				Log.i(TAG, "���ظ�������");
				footerView.setPadding(0, 0, 0, 0);
				this.setSelection(this.getCount());

				if (mOnRefershListener != null) {
					mOnRefershListener.onLoadingMore();
				}
			}
		}
	}

	/**
	 * ������ʱ����
	 * 
	 * @param firstVisibleItem
	 *            ��ǰ��Ļ��ʾ�ڶ�����item��position
	 * @param visibleItemCount
	 *            ��ǰ��Ļ��ʾ�˶��ٸ���Ŀ������
	 * @param totalItemCount
	 *            ListView������Ŀ������
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		firstVisibleItemPosition = firstVisibleItem;

		if (getLastVisiblePosition() == (totalItemCount - 1)) {
			isScrollToBottom = true;
		} else {
			isScrollToBottom = false;
		}
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		mOnRefershListener = listener;
	}


	public void hideHeaderView() {
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		ivArrow.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		tvState.setText("加载更多");
		tvLastUpdateTime.setText("上次更新时间：" + getLastUpdateTime());
		currentState = DOWN_PULL_REFRESH;
	}

	public void hideFooterView() {
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		isLoadingMore = false;
	}
}
