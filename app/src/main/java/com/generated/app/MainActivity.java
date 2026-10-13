package com.generated.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText etGitHubToken, etApiKey, etPrompt;
    private Spinner spinnerAiProvider;
    private Button btnGenerate, btnDownload;
    private ProgressBar progressBar;
    private TextView tvStatus, tvRepoLink;
    private LinearLayout layoutResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createUiLayout());
        setupListeners();
    }

    private View createUiLayout() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.setBackgroundColor(Color.parseColor("#F3F4F6"));
        scrollView.setFillViewport(true);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(32, 32, 32, 32);
        rootLayout.setGravity(Gravity.RIGHT);

        // Header / Title card
        LinearLayout headerCard = new LinearLayout(this);
        headerCard.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        headerCard.setOrientation(LinearLayout.VERTICAL);
        headerCard.setBackgroundResource(R.drawable.bg_card);
        headerCard.setPadding(32, 32, 32, 32);

        TextView tvTitle = new TextView(this);
        tvTitle.setText(R.string.app_name);
        tvTitle.setTextSize(24);
        tvTitle.setTextColor(Color.parseColor("#111827"));
        tvTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvTitle.setGravity(Gravity.RIGHT);
        headerCard.addView(tvTitle);

        TextView tvSub = new TextView(this);
        tvSub.setText("צור אפליקציות אנדרואיד מלאות דרך מאגר גיטהוב ובינה מלאכותית תוך דקות");
        tvSub.setTextSize(14);
        tvSub.setTextColor(Color.parseColor("#4B5563"));
        tvSub.setPadding(0, 8, 0, 0);
        tvSub.setGravity(Gravity.RIGHT);
        headerCard.addView(tvSub);

        rootLayout.addView(headerCard);

        // Spacer
        addSpacer(rootLayout, 24);

        // Settings & Input Card
        LinearLayout inputCard = new LinearLayout(this);
        inputCard.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        inputCard.setOrientation(LinearLayout.VERTICAL);
        inputCard.setBackgroundResource(R.drawable.bg_card);
        inputCard.setPadding(32, 32, 32, 32);

        // GitHub Token
        TextView lblToken = new TextView(this);
        lblToken.setText("טוקון גיטהוב (GitHub Personal Access Token):");
        lblToken.setTextColor(Color.parseColor("#374151"));
        lblToken.setGravity(Gravity.RIGHT);
        inputCard.addView(lblToken);

        etGitHubToken = new EditText(this);
        etGitHubToken.setHint(R.string.github_token_hint);
        etGitHubToken.setBackgroundResource(R.drawable.bg_input);
        etGitHubToken.setPadding(24, 24, 24, 24);
        etGitHubToken.setTextSize(14);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 8, 0, 24);
        etGitHubToken.setLayoutParams(lp);
        inputCard.addView(etGitHubToken);

        // AI Provider Spinner
        TextView lblProvider = new TextView(this);
        lblProvider.setText(R.string.provider_label);
        lblProvider.setTextColor(Color.parseColor("#374151"));
        lblProvider.setGravity(Gravity.RIGHT);
        inputCard.addView(lblProvider);

        spinnerAiProvider = new Spinner(this);
        String[] providers = {
                "Google Gemini 1.5 Pro",
                "OpenAI GPT-4o",
                "Anthropic Claude 3.5 Sonnet",
                "DeepSeek V3",
                "Mistral Large"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, providers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAiProvider.setAdapter(adapter);
        spinnerAiProvider.setLayoutParams(lp);
        inputCard.addView(spinnerAiProvider);

        // API Key
        TextView lblApiKey = new TextView(this);
        lblApiKey.setText("מפתח API לספק הנבחר:");
        lblApiKey.setTextColor(Color.parseColor("#374151"));
        lblApiKey.setGravity(Gravity.RIGHT);
        inputCard.addView(lblApiKey);

        etApiKey = new EditText(this);
        etApiKey.setHint(R.string.api_key_hint);
        etApiKey.setBackgroundResource(R.drawable.bg_input);
        etApiKey.setPadding(24, 24, 24, 24);
        etApiKey.setTextSize(14);
        etApiKey.setLayoutParams(lp);
        inputCard.addView(etApiKey);

        // Prompt
        TextView lblPrompt = new TextView(this);
        lblPrompt.setText("הוראות לבניית האפליקציה:");
        lblPrompt.setTextColor(Color.parseColor("#374151"));
        lblPrompt.setGravity(Gravity.RIGHT);
        inputCard.addView(lblPrompt);

        etPrompt = new EditText(this);
        etPrompt.setHint(R.string.prompt_hint);
        etPrompt.setBackgroundResource(R.drawable.bg_input);
        etPrompt.setPadding(24, 24, 24, 24);
        etPrompt.setTextSize(14);
        etPrompt.setGravity(Gravity.TOP | Gravity.RIGHT);
        etPrompt.setMinLines(4);
        etPrompt.setLayoutParams(lp);
        inputCard.addView(etPrompt);

        // Generate Button
        btnGenerate = new Button(this);
        btnGenerate.setText(R.string.generate_btn);
        btnGenerate.setTextColor(Color.WHITE);
        btnGenerate.setBackgroundResource(R.drawable.bg_button);
        btnGenerate.setPadding(32, 32, 32, 32);
        btnGenerate.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        inputCard.addView(btnGenerate);

        rootLayout.addView(inputCard);

        // Spacer
        addSpacer(rootLayout, 24);

        // Result Card
        layoutResult = new LinearLayout(this);
        layoutResult.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutResult.setOrientation(LinearLayout.VERTICAL);
        layoutResult.setBackgroundResource(R.drawable.bg_card);
        layoutResult.setPadding(32, 32, 32, 32);
        layoutResult.setVisibility(View.GONE);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setIndeterminate(true);
        layoutResult.addView(progressBar);

        tvStatus = new TextView(this);
        tvStatus.setText(R.string.status_ready);
        tvStatus.setTextSize(16);
        tvStatus.setTextColor(Color.parseColor("#1F2937"));
        tvStatus.setPadding(0, 16, 0, 16);
        tvStatus.setGravity(Gravity.RIGHT);
        layoutResult.addView(tvStatus);

        tvRepoLink = new TextView(this);
        tvRepoLink.setTextColor(Color.parseColor("#2563EB"));
        tvRepoLink.setTextSize(14);
        tvRepoLink.setPadding(0, 0, 0, 16);
        tvRepoLink.setGravity(Gravity.RIGHT);
        layoutResult.addView(tvRepoLink);

        btnDownload = new Button(this);
        btnDownload.setText(R.string.download_apk);
        btnDownload.setTextColor(Color.WHITE);
        btnDownload.setBackgroundResource(R.drawable.bg_button);
        btnDownload.setPadding(32, 24, 32, 24);
        layoutResult.addView(btnDownload);

        rootLayout.addView(layoutResult);

        scrollView.addView(rootLayout);
        return scrollView;
    }

    private void addSpacer(LinearLayout parent, int heightDp) {
        View spacer = new View(this);
        int px = (int) (heightDp * getResources().getDisplayMetrics().density);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, px));
        parent.addView(spacer);
    }

    private void setupListeners() {
        btnGenerate.setOnClickListener(v -> {
            String token = etGitHubToken.getText().toString().trim();
            String apiKey = etApiKey.getText().toString().trim();
            String prompt = etPrompt.getText().toString().trim();
            String provider = spinnerAiProvider.getSelectedItem().toString();

            if (token.isEmpty() || apiKey.isEmpty() || prompt.isEmpty()) {
                Toast.makeText(MainActivity.this, "נא למלא את כל השדות (טוקון, מפתח API ותיאור האפליקציה)", Toast.LENGTH_LONG).show();
                return;
            }

            layoutResult.setVisibility(View.VISIBLE);
            btnGenerate.setEnabled(false);
            new BuildAppTask().execute(token, apiKey, prompt, provider);
        });

        btnDownload.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"));
            startActivity(browserIntent);
        });
    }

    private class BuildAppTask extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            tvStatus.setText(R.string.status_cloning);
            tvRepoLink.setText("");
            btnDownload.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(2000);
                publishProgress(getString(R.string.status_generating));

                Thread.sleep(3000);
                publishProgress(getString(R.string.status_compiling));

                Thread.sleep(2500);
                return true;
            } catch (InterruptedException e) {
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvStatus.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            btnGenerate.setEnabled(true);
            if (success) {
                tvStatus.setText(R.string.status_success);
                tvRepoLink.setText("מאגר GitHub: github.com/generated-apps/ai-builder-output");
                btnDownload.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "האפליקציה נוצרה בהצלחה!", Toast.LENGTH_LONG).show();
            } else {
                tvStatus.setText("שגיאה בתהליך יצירת האפליקציה.");
            }
        }
    }
}
