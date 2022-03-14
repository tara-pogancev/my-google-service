import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'export-page',
  templateUrl: './export-page.component.html',
  styleUrls: ['./export-page.component.scss'],
})
export class ExportPageComponent implements OnInit {
  exportForm: FormGroup = new FormGroup({});
  downloadJsonHref: any;
  downloadCsvHref: any;

  constructor(
    private exportService: ExportService,
    private _sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.exportForm = new FormGroup({
      type: new FormControl('csv', {
        validators: [Validators.required],
      }),
    });
  }

  export() {
    if (this.exportForm.valid) {
      if (this.exportForm.controls.type.value == 'csv') {
        this.exportService.exportAllCsv().subscribe((res) => {
          var url = window.URL.createObjectURL(res.body);
          window.open(url);
        });
      } else {
        this.exportService.exportAllJson().subscribe((res) => {
          var url = window.URL.createObjectURL(res.body);
          window.open(url);
        });
      }
    }
  }

  encode = function (s: any) {
    var out = [];
    for (var i = 0; i < s.length; i++) {
      out[i] = s.charCodeAt(i);
    }
    return new Uint8Array(out);
  };
}
