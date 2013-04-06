namespace LocalDBUtility
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.button1 = new System.Windows.Forms.Button();
            this.tbXFilePath = new System.Windows.Forms.TextBox();
            this.ofdXmlFile = new System.Windows.Forms.OpenFileDialog();
            this.button2 = new System.Windows.Forms.Button();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.lCFileStatus = new System.Windows.Forms.Label();
            this.tbCSFilePath = new System.Windows.Forms.TextBox();
            this.bProcessCSV = new System.Windows.Forms.Button();
            this.bOpenCSV = new System.Windows.Forms.Button();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lXFilePath = new System.Windows.Forms.Label();
            this.dsSessions = new System.Data.DataSet();
            this.dgSessions = new System.Windows.Forms.DataGridView();
            this.dgGroups = new System.Windows.Forms.DataGridView();
            this.dsGroups = new System.Data.DataSet();
            this.btAssignGrp = new System.Windows.Forms.Button();
            this.ofdCSFile = new System.Windows.Forms.OpenFileDialog();
            this.tbSessDir = new System.Windows.Forms.TextBox();
            this.btProcessSessionDir = new System.Windows.Forms.Button();
            this.btSelectSessDir = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.fbdSessionDir = new System.Windows.Forms.FolderBrowserDialog();
            this.tabControl1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dsSessions)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSessions)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgGroups)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dsGroups)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(644, 19);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 0;
            this.button1.Text = "Select File";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // tbXFilePath
            // 
            this.tbXFilePath.Location = new System.Drawing.Point(16, 22);
            this.tbXFilePath.Name = "tbXFilePath";
            this.tbXFilePath.Size = new System.Drawing.Size(545, 20);
            this.tbXFilePath.TabIndex = 1;
            this.tbXFilePath.Text = "select 1 file...";
            // 
            // ofdXmlFile
            // 
            this.ofdXmlFile.FileName = "openFileDialog1";
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(644, 48);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 2;
            this.button2.Text = "Write";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Location = new System.Drawing.Point(9, 38);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(735, 114);
            this.tabControl1.TabIndex = 3;
            this.tabControl1.SelectedIndexChanged += new System.EventHandler(this.tabControl1_SelectedIndexChanged);
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.lCFileStatus);
            this.tabPage2.Controls.Add(this.tbCSFilePath);
            this.tabPage2.Controls.Add(this.bProcessCSV);
            this.tabPage2.Controls.Add(this.bOpenCSV);
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(727, 88);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Anthropometric data feed";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // lCFileStatus
            // 
            this.lCFileStatus.AutoSize = true;
            this.lCFileStatus.Location = new System.Drawing.Point(19, 58);
            this.lCFileStatus.Name = "lCFileStatus";
            this.lCFileStatus.Size = new System.Drawing.Size(16, 13);
            this.lCFileStatus.TabIndex = 7;
            this.lCFileStatus.Text = "...";
            // 
            // tbCSFilePath
            // 
            this.tbCSFilePath.Location = new System.Drawing.Point(19, 23);
            this.tbCSFilePath.Name = "tbCSFilePath";
            this.tbCSFilePath.Size = new System.Drawing.Size(545, 20);
            this.tbCSFilePath.TabIndex = 5;
            this.tbCSFilePath.Text = "select 1 .csv file...";
            // 
            // bProcessCSV
            // 
            this.bProcessCSV.Location = new System.Drawing.Point(615, 49);
            this.bProcessCSV.Name = "bProcessCSV";
            this.bProcessCSV.Size = new System.Drawing.Size(75, 23);
            this.bProcessCSV.TabIndex = 6;
            this.bProcessCSV.Text = "Feed CSV";
            this.bProcessCSV.UseVisualStyleBackColor = true;
            this.bProcessCSV.Click += new System.EventHandler(this.bProcessCSV_Click);
            // 
            // bOpenCSV
            // 
            this.bOpenCSV.Location = new System.Drawing.Point(615, 20);
            this.bOpenCSV.Name = "bOpenCSV";
            this.bOpenCSV.Size = new System.Drawing.Size(75, 23);
            this.bOpenCSV.TabIndex = 4;
            this.bOpenCSV.Text = "Open CSV";
            this.bOpenCSV.UseVisualStyleBackColor = true;
            this.bOpenCSV.Click += new System.EventHandler(this.bOpenCSV_Click);
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.lXFilePath);
            this.tabPage1.Controls.Add(this.tbXFilePath);
            this.tabPage1.Controls.Add(this.button2);
            this.tabPage1.Controls.Add(this.button1);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(727, 93);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "XML attribs feed";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // lXFilePath
            // 
            this.lXFilePath.AutoSize = true;
            this.lXFilePath.Location = new System.Drawing.Point(16, 57);
            this.lXFilePath.Name = "lXFilePath";
            this.lXFilePath.Size = new System.Drawing.Size(16, 13);
            this.lXFilePath.TabIndex = 3;
            this.lXFilePath.Text = "...";
            // 
            // dsSessions
            // 
            this.dsSessions.DataSetName = "SessionsDataSet";
            this.dsSessions.Locale = new System.Globalization.CultureInfo("pl-PL");
            // 
            // dgSessions
            // 
            this.dgSessions.AllowUserToAddRows = false;
            this.dgSessions.AllowUserToDeleteRows = false;
            this.dgSessions.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgSessions.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.dgSessions.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgSessions.Location = new System.Drawing.Point(9, 158);
            this.dgSessions.MultiSelect = false;
            this.dgSessions.Name = "dgSessions";
            this.dgSessions.ReadOnly = true;
            this.dgSessions.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgSessions.Size = new System.Drawing.Size(719, 664);
            this.dgSessions.TabIndex = 4;
            this.dgSessions.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgSessions_CellContentClick);
            // 
            // dgGroups
            // 
            this.dgGroups.AllowUserToAddRows = false;
            this.dgGroups.AllowUserToDeleteRows = false;
            this.dgGroups.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgGroups.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgGroups.Cursor = System.Windows.Forms.Cursors.Default;
            this.dgGroups.Location = new System.Drawing.Point(758, 383);
            this.dgGroups.MultiSelect = false;
            this.dgGroups.Name = "dgGroups";
            this.dgGroups.ReadOnly = true;
            this.dgGroups.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgGroups.Size = new System.Drawing.Size(274, 296);
            this.dgGroups.TabIndex = 5;
            // 
            // dsGroups
            // 
            this.dsGroups.DataSetName = "GroupsDataSet";
            // 
            // btAssignGrp
            // 
            this.btAssignGrp.Location = new System.Drawing.Point(840, 340);
            this.btAssignGrp.Name = "btAssignGrp";
            this.btAssignGrp.Size = new System.Drawing.Size(105, 23);
            this.btAssignGrp.TabIndex = 6;
            this.btAssignGrp.Text = "Assign group";
            this.btAssignGrp.UseVisualStyleBackColor = true;
            this.btAssignGrp.Click += new System.EventHandler(this.btAssignGrp_Click);
            // 
            // ofdCSFile
            // 
            this.ofdCSFile.FileName = "openFileDialog2";
            // 
            // tbSessDir
            // 
            this.tbSessDir.Location = new System.Drawing.Point(137, 12);
            this.tbSessDir.Name = "tbSessDir";
            this.tbSessDir.Size = new System.Drawing.Size(545, 20);
            this.tbSessDir.TabIndex = 8;
            this.tbSessDir.Text = "select a directory...";
            this.tbSessDir.TextChanged += new System.EventHandler(this.tbSessDir_TextChanged);
            // 
            // btProcessSessionDir
            // 
            this.btProcessSessionDir.Location = new System.Drawing.Point(816, 9);
            this.btProcessSessionDir.Name = "btProcessSessionDir";
            this.btProcessSessionDir.Size = new System.Drawing.Size(75, 23);
            this.btProcessSessionDir.TabIndex = 9;
            this.btProcessSessionDir.Text = "Write session";
            this.btProcessSessionDir.UseVisualStyleBackColor = true;
            this.btProcessSessionDir.Click += new System.EventHandler(this.btProcessSessionDir_Click);
            // 
            // btSelectSessDir
            // 
            this.btSelectSessDir.Location = new System.Drawing.Point(711, 9);
            this.btSelectSessDir.Name = "btSelectSessDir";
            this.btSelectSessDir.Size = new System.Drawing.Size(75, 23);
            this.btSelectSessDir.TabIndex = 7;
            this.btSelectSessDir.Text = "Select Directory";
            this.btSelectSessDir.UseVisualStyleBackColor = true;
            this.btSelectSessDir.Click += new System.EventHandler(this.btSelectSessDir_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.label1.Location = new System.Drawing.Point(6, 19);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(125, 13);
            this.label1.TabIndex = 10;
            this.label1.Text = "Directory of new session:";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1075, 834);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.tbSessDir);
            this.Controls.Add(this.btProcessSessionDir);
            this.Controls.Add(this.btSelectSessDir);
            this.Controls.Add(this.btAssignGrp);
            this.Controls.Add(this.dgGroups);
            this.Controls.Add(this.dgSessions);
            this.Controls.Add(this.tabControl1);
            this.Name = "Form1";
            this.Text = "LocalDBUtility";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.tabControl1.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dsSessions)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgSessions)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgGroups)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dsGroups)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox tbXFilePath;
        private System.Windows.Forms.OpenFileDialog ofdXmlFile;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Data.DataSet dsSessions;
        private System.Windows.Forms.DataGridView dgSessions;
        private System.Windows.Forms.Label lXFilePath;
        private System.Windows.Forms.DataGridView dgGroups;
        private System.Data.DataSet dsGroups;
        private System.Windows.Forms.Button btAssignGrp;
        private System.Windows.Forms.Label lCFileStatus;
        private System.Windows.Forms.TextBox tbCSFilePath;
        private System.Windows.Forms.Button bProcessCSV;
        private System.Windows.Forms.Button bOpenCSV;
        private System.Windows.Forms.OpenFileDialog ofdCSFile;
        private System.Windows.Forms.TextBox tbSessDir;
        private System.Windows.Forms.Button btProcessSessionDir;
        private System.Windows.Forms.Button btSelectSessDir;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.FolderBrowserDialog fbdSessionDir;

    }
}

